package vip.breakpoint.remote.bean;

/**
 * 修改配置的vo
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/29
 * 欢迎关注公众号:代码废柴
 */
public class RemoteConfigVo {

    /**
     * 配置的 key
     */
    private String configKey;
    /**
     * 更新的 value
     */
    private String configValue;

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
