package vip.breakpoint.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import vip.breakpoint.utils.EasyStringUtils;

/**
 * 用户缓存的操作
 *
 * @author : breakpoint
 * create on 2022/09/07
 * 欢迎关注公众号 《代码废柴》
 */
public class LocalUserCache<T> {

    // users
    private static final Cache<String, Object> cache = CacheBuilder.newBuilder().build();

    // 添加用户
    public static void addUser(String token, Object user) {
        if (EasyStringUtils.isBlank(token) || null == user) {
            return;
        }
        cache.put(token, user);
    }

    // 获取当前的登录用户
    @SuppressWarnings("unchecked")
    public static <T> T getLoginUser(String token, Class<T> clazz) {
        if (EasyStringUtils.isBlank(token)) {
            return null;
        }
        Object ret = cache.getIfPresent(token);
        if (null != ret && ret.getClass().isAssignableFrom(clazz)) {
            return (T) ret;
        }
        return null;
    }

    public static Object getLoginUser(String token) {
        if (EasyStringUtils.isBlank(token)) {
            return null;
        }
        return cache.getIfPresent(token);
    }
}
