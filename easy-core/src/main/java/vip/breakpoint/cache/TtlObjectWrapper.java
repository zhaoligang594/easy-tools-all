package vip.breakpoint.cache;

import java.util.Objects;

/**
 * 带有缓存时间的缓存对象
 *
 * @author : breakpoint/赵先生
 * create on 2022/11/01
 * 欢迎关注公众号:代码废柴
 */
public class TtlObjectWrapper<T> {

    private String key; // key
    private T data; // 数据信息
    private long lastAccessTime;// 上一次访问的时间
    private long ttl; // 生存时间 ms

    public TtlObjectWrapper(String key, T data, long lastAccessTime, long ttl) {
        this.key = key;
        this.data = data;
        this.lastAccessTime = lastAccessTime;
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TtlObjectWrapper<?> that = (TtlObjectWrapper<?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
