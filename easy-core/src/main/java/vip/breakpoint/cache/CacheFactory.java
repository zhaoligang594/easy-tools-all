package vip.breakpoint.cache;

import vip.breakpoint.annotation.MParam;
import vip.breakpoint.utils.EasyIDUtils;

import java.util.function.Supplier;


/**
 * CacheFactory
 *
 * @author breakpoint/赵先生
 * create on 2021/02/21
 */
public final class CacheFactory {

    private CacheFactory() { /*refuse new obj*/}

    // 获取缓存
    public static <T> Cache<T> newPerpetualCacheInstance(@MParam("存储类型") Class<T> T) {
        // 返回LRU CACHE
        return new PerpetualCache<>(EasyIDUtils.generateIdStr());
    }

    // 获取缓存
    public static <T> Cache<T> newCacheInstance(@MParam("存储类型") Class<T> T,
                                                @MParam("大小") Supplier<Integer> size) {
        // 返回LRU CACHE
        return new LruCache<T>(
                new PerpetualCache<>(EasyIDUtils.generateIdStr()),
                size.get()
        );
    }

    // 获取缓存
    public static <T> Cache<T> newCacheInstance(@MParam("存储类型") Class<T> T,
                                                @MParam("大小") Integer size) {
        // 返回LRU CACHE
        return new LruCache<T>(
                new PerpetualCache<>(EasyIDUtils.generateIdStr()),
                size
        );
    }

    public static <T> TtlCache<T> newVersionCacheInstance(@MParam("大小") Supplier<Integer> size) {
        // 返回操作的cache
        return new ExpireWithTtlCache<>(size);
    }

    public static <T> TtlCache<T> newVersionCacheInstance(@MParam("大小") Integer size) {
        // 返回操作的cache
        return new ExpireWithTtlCache<>(() -> size);
    }
}
