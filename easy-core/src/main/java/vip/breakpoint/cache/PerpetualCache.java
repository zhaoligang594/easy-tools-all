package vip.breakpoint.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;


/**
 * 永久性的缓存
 *
 * @author breakpoint/赵先生
 * create on 2021/02/03
 */
public class PerpetualCache<T> implements Cache<T> {

    private final String id; // id
    private final Map<String, T> cache = new HashMap<String, T>();

    public PerpetualCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public void putObject(String key, T value) {
        cache.put(key, value);
    }

    @Override
    public T getObject(String key) {
        return cache.get(key);
    }

    @Override
    public T removeObject(String key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (getId() == null) {
            throw new CacheException("Cache instances require an ID.");
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cache)) {
            return false;
        }
        Cache<?> otherCache = (Cache<?>) o;
        return getId().equals(otherCache.getId());
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            throw new CacheException("Cache instances require an ID.");
        }
        return getId().hashCode();
    }
}
