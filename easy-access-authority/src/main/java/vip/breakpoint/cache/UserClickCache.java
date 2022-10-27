package vip.breakpoint.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import vip.breakpoint.bean.ClickIntervalInfo;
import vip.breakpoint.utils.EasyStringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 用户点击操作的本地缓存
 *
 * @author : breakpoint
 * create on 2022/09/07
 * 欢迎关注公众号 《代码废柴》
 */
public class UserClickCache {

    // users
    private static final Cache<String, ClickIntervalInfo> cache =
            CacheBuilder.newBuilder()
                    .expireAfterAccess(10L, TimeUnit.SECONDS)
                    .build();

    // 添加点击信息
    public static void add(String token, ClickIntervalInfo clickIntervalInfo) {
        if (EasyStringUtils.isBlank(token) || null == clickIntervalInfo) {
            return;
        }
        cache.put(token, clickIntervalInfo);
    }

    // 获取上一次点击信息
    public static ClickIntervalInfo get(String token) {
        if (EasyStringUtils.isBlank(token)) {
            return null;
        }
        return cache.getIfPresent(token);
    }
}
