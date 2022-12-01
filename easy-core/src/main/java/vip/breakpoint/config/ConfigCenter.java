package vip.breakpoint.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取的配置的中心
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/30
 * 欢迎关注公众号:代码废柴
 */
public class ConfigCenter {

    /**
     * 是否开启项目中的日志能力
     */
    public static final String ENABLE_LOG_IN_APP_KEY = "enableLogInApp";
    /**
     * 是否开启了RBAC的能力
     */
    public static final String ENABLE_RBAC_KEY = "enableRBAC";

    private static final Map<String, Object> key2ValueMap = new HashMap<>();

    public static void addConfig(String key, Object value) {
        if (null != key) {
            key2ValueMap.put(key, value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(String key, Class<T> clazz) {
        Object o = key2ValueMap.get(key);
        if (null != o && clazz.isAssignableFrom(o.getClass())) {
            return (T) o;
        }
        return null;
    }
}
