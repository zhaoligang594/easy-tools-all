package vip.breakpoint.cache;

import java.util.concurrent.TimeUnit;

/**
 * 带有版本的、过期时间的缓存
 * 默认的缓存是30分钟
 *
 * @author breakpoint/赵先生 <zlgtop@163.com>
 * create on 2021/02/03
 */
public interface VersionCache<T> extends Cache<T> {

    Long DEFAULT_TIME_OUT = 1000L;

    // set running
    void setRunning(boolean running);

    // 放入对象
    void putObject(String key, T value, long ttl, TimeUnit timeUnit);

    // 清理过期数据
    void clearExpireData();

    @Override
    default void putObject(String key, T value) {
        // 采用默认的保存的方式
        this.putObject(key, value, DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS);
    }
}
