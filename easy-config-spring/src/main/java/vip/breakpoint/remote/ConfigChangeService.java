package vip.breakpoint.remote;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/27
 * 欢迎关注公众号:代码废柴
 */
public interface ConfigChangeService {

    /**
     * 更新配置的操作
     *
     * @param configKey   配置的key
     * @param configValue 配置的value
     * @return 是否操作成功
     */
    boolean doChangeConfig(String configKey, String configValue);
}
