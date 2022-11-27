package vip.breakpoint.cache;


import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.EasyIDUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 带有版本号的cache
 *
 * @author breakpoint/赵先生
 * create on 2021/03/24
 */
public class ExpireWithTtlCache<T> implements TtlCache<T> {

    private static final Logger log = WebLogFactory.getLogger(ExpireWithTtlCache.class);

    // 包装类 进行 对于我们存储的对象进行操作
    private final Cache<TtlObjectWrapper<T>> delegate;

    public ExpireWithTtlCache(Supplier<Integer> size) {
        this.delegate = new LruCache<>(
                new PerpetualCache<>(EasyIDUtils.generateIdStr()),
                size.get());
        // 清理 cache
        TtlClearContext.addClearCache(this);
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public int getSize() {
        return delegate.getSize();
    }

    @Override
    public void putObject(String key, T value, long ttl, TimeUnit timeUnit) {
        // 转换成包装类
        TtlObjectWrapper<T> wrapper = new TtlObjectWrapper<>(key, value,
                System.currentTimeMillis(), timeUnit.toMillis(ttl));
        delegate.putObject(key, wrapper);
    }

    @Override
    public T getObject(String key) {
        TtlObjectWrapper<T> wrapper = delegate.getObject(key);
        if (null != wrapper) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - wrapper.getLastAccessTime() > wrapper.getTtl()) {
                // 说明数据过期了
                this.removeObject(key);
                return null;
            } else {
                this.putObject(key, wrapper.getData(), wrapper.getTtl(), TimeUnit.MILLISECONDS);
                return wrapper.getData();
            }
        } else {
            return null;
        }
    }

    @Override
    public T removeObject(String key) {
        TtlObjectWrapper<T> wrapper = delegate.removeObject(key);
        return null != wrapper ? wrapper.getData() : null;
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public void clearExpireData() {
        List<TtlObjectWrapper<T>> allData = delegate.getAll();
        if (null != allData) {
            for (int i = 0; i < allData.size(); i++) {
                TtlObjectWrapper<T> wrapper = allData.get(i);
                if (!isNotExpire(wrapper)) {
                    // 失效了 进行删除hh
                    log.warn("the expire data have cleared from the cache; key:{}", wrapper.getKey());
                    this.removeObject(wrapper.getKey());
                }
                // for gc
                if (i % 5000 == 0) {
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        log.error("clearExpireData", e);
                    }
                }
            }
        }
    }

    // 判断是否是过期的数据
    private boolean isNotExpire(TtlObjectWrapper<T> wrapper) {
        return System.currentTimeMillis() - wrapper.getLastAccessTime() < wrapper.getTtl();
    }

    @Override
    public List<T> getAll() {
        List<T> res = new ArrayList<>();
        List<TtlObjectWrapper<T>> delegateAll = delegate.getAll();
        // 返回操作的数据
        if (null != delegateAll) {
            List<TtlObjectWrapper<T>> collect = delegateAll.stream()
                    .filter(this::isNotExpire).collect(Collectors.toList());
            collect.forEach(delegate -> res.add(delegate.getData()));
            collect.clear();
        }
        return res;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return delegate.getReadWriteLock();
    }
}
