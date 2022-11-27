package vip.breakpoint.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 永久性的缓存
 *
 * @author breakpoint/赵先生
 * create on 2021/02/03
 */
public class PerpetualCache<T> implements Cache<T> {

    private final String id; // id
    private final Map<String, T> cache = new HashMap<String, T>();

    private static final long DEFAULT_TRY_LOCK_TIME = 500;

    private volatile ReadWriteLock readWriteLock;

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
        ReadWriteLock readWriteLock = getReadWriteLock();
        Lock lock = readWriteLock.writeLock();
        try {
            if (lock.tryLock(DEFAULT_TRY_LOCK_TIME, TimeUnit.MILLISECONDS)) {
                cache.put(key, value);
            }
        } catch (InterruptedException e) {
            // omit...
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T getObject(String key) {
        ReadWriteLock readWriteLock = getReadWriteLock();
        Lock lock = readWriteLock.readLock();
        try {
            if (lock.tryLock(DEFAULT_TRY_LOCK_TIME, TimeUnit.MILLISECONDS)) {
                return cache.get(key);
            }
        } catch (InterruptedException e) {
            // omit...
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public T removeObject(String key) {
        ReadWriteLock readWriteLock = getReadWriteLock();
        Lock lock = readWriteLock.writeLock();
        try {
            if (lock.tryLock(DEFAULT_TRY_LOCK_TIME, TimeUnit.MILLISECONDS)) {
                return cache.remove(key);
            }
        } catch (InterruptedException e) {
            // omit...
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public List<T> getAll() {
        ReadWriteLock readWriteLock = getReadWriteLock();
        Lock lock = readWriteLock.readLock();
        try {
            if (lock.tryLock(DEFAULT_TRY_LOCK_TIME, TimeUnit.MILLISECONDS)) {
                return new ArrayList<>(cache.values());
            }
        } catch (InterruptedException e) {
            // omit...
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        if (null == readWriteLock) {
            synchronized (this) {
                if (null == readWriteLock) {
                    readWriteLock = new ReentrantReadWriteLock();
                }
            }
        }
        return readWriteLock;
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
