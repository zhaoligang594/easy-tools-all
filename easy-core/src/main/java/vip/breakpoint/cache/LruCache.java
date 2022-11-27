package vip.breakpoint.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 最近最久未使用 缓存
 *
 * @author breakpoint/赵先生
 * create on 2021/02/03
 */
public class LruCache<T> implements Cache<T> {

    private final Cache<T> delegate; // 实际的操作对象

    private final Map<String, String> keyMap; // key 的 索引
    private String eldestKey; // 最老的key

    public LruCache(Cache<T> delegate, int size) {
        this.delegate = delegate;
        // 设置大小
        keyMap = new LinkedHashMap<String, String>(size, .75F, true) {
            private static final long serialVersionUID = -5475440531009343789L;

            // after put node , invoke this method
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                boolean tooBig = size() > size;
                if (tooBig) {
                    eldestKey = eldest.getKey();
                }
                return tooBig;
            }
        };
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
    public void putObject(String key, T value) {
        delegate.putObject(key, value);
        cycleKeyList(key);
    }

    @Override
    public T getObject(String key) {
        keyMap.get(key); //touch
        return delegate.getObject(key);
    }

    @Override
    public T removeObject(String key) {
        return delegate.removeObject(key);
    }

    @Override
    public void clear() {
        delegate.clear();
        keyMap.clear();
    }

    @Override
    public List<T> getAll() {
        return delegate.getAll();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return delegate.getReadWriteLock();
    }

    private void cycleKeyList(String key) {
        keyMap.put(key, key);
        if (eldestKey != null) {
            delegate.removeObject(eldestKey);
            eldestKey = null;
        }
    }
}
