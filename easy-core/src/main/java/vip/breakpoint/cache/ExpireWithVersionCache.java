package vip.breakpoint.cache;


import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.EasyIDUtils;
import vip.breakpoint.utils.ExecutorServiceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
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
public class ExpireWithVersionCache<T> implements VersionCache<T> {

    private static final Logger log = WebLogFactory.getLogger(ExpireWithVersionCache.class);

    private static final class ExecutorServiceClass {
        // 单例模式 并且使用的时候再 初始化 节省资源
        private static final ExecutorService executor =
                ExecutorServiceUtils.getSingleExecutorService(() -> 500L, TimeUnit.MILLISECONDS,
                        () -> "expireWithVersionCache");
    }

    private static final long DEFAULT_INTERVAL_TIMES = 1000L;

    private volatile boolean running = true;

    // 包装类 进行 对于我们存储的对象进行操作
    private final Cache<TVersionWrapper<T>> delegate;

    public ExpireWithVersionCache(Supplier<Integer> size) {
        this(size, DEFAULT_INTERVAL_TIMES);
    }

    // set running
    public void setRunning(boolean running) {
        this.running = running;
    }

    public ExpireWithVersionCache(Supplier<Integer> size, long clearIntervalTimes) {
        this.delegate = new LruCache<>(
                new PerpetualCache<>(EasyIDUtils.generateIdStr()),
                size.get());
        if (clearIntervalTimes < DEFAULT_INTERVAL_TIMES)
            throw new IllegalArgumentException("clearIntervalTimes must be bigger than "
                    + DEFAULT_INTERVAL_TIMES + "ms");
        ExecutorServiceClass.executor.execute(() -> {
            while (running) {
                this.clearExpireData();
                if (!running) {
                    break;
                }
                try {
                    Thread.sleep(clearIntervalTimes);
                } catch (final InterruptedException ignored) {
                    // ignore
                }
            }
        });
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
        TVersionWrapper<T> wrapper = new TVersionWrapper<>(key, value,
                System.currentTimeMillis(), timeUnit.toMillis(ttl));
        delegate.putObject(key, wrapper);
    }

    @Override
    public T getObject(String key) {
        TVersionWrapper<T> wrapper = delegate.getObject(key);
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
        TVersionWrapper<T> wrapper = delegate.removeObject(key);
        return null != wrapper ? wrapper.getData() : null;
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public void clearExpireData() {
        List<TVersionWrapper<T>> allData = delegate.getAll();
        for (TVersionWrapper<T> wrapper : allData) {
            if (!isNotExpire(wrapper)) {
                // 失效了 进行删除hh
                log.warn("the expire date have cleared from the cache; key:{}", wrapper.getKey());
                this.removeObject(wrapper.getKey());
            }
        }
    }

    // 判断是否是过期的数据
    private boolean isNotExpire(TVersionWrapper<T> wrapper) {
        return System.currentTimeMillis() - wrapper.getLastAccessTime() < wrapper.getTtl();
    }

    @Override
    public List<T> getAll() {
        List<T> res = new ArrayList<>();
        List<TVersionWrapper<T>> delegateAll = delegate.getAll();
        // 返回操作的数据
        if (null != delegateAll) {
            List<TVersionWrapper<T>> collect = delegateAll.stream()
                    .filter(this::isNotExpire).collect(Collectors.toList());
            collect.forEach(delegate -> res.add(delegate.getData()));
            collect.clear();
            collect = null;
        }
        return res;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
