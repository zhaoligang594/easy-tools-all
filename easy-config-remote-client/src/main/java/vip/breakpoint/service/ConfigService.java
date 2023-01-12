package vip.breakpoint.service;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
public interface ConfigService {
    /**
     * 获取到配置中心的 host 配置
     */
    String CONFIG_CENTER_REMOTE_HOST_URI = "/config/center/getRemoteConfigList";
    /**
     * 获取配置格局 key
     */
    String CONFIG_CENTER_CONFIG_VALUE_URI = "/config/center/getConfigValue";

    // 获取到请求的   uri
    default String getRealUri(String host, Integer port, String uri) {
        return String.format("http://%s:%s/%s", host, port, uri);
    }
}
