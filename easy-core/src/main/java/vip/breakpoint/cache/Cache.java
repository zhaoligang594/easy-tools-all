package vip.breakpoint.cache;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 缓存接口
 *
 * @author breakpoint/赵先生
 * create on 2021/02/03
 */
public interface Cache<T> {
    // get ID
    String getId();

    // get SIZE
    int getSize();

    // 放入对象
    void putObject(String key, T value);

    // 获取到对象
    T getObject(String key);

    // 移除对象
    T removeObject(String key);

    // 清除所有的对象
    void clear();

    // 获取所有的缓存
    List<T> getAll();

    // 获取锁的结构
    ReadWriteLock getReadWriteLock();
}
