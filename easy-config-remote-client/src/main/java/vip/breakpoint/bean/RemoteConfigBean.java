package vip.breakpoint.bean;

/**
 * 远端接口的配置
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
public class RemoteConfigBean {
    /**
     * 远端的IP地址
     */
    private String remoteHost;
    /**
     * 接口地址
     */
    private Integer remotePort;
    /**
     * 访问接口的 token
     */
    private String infToken;

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    public String getInfToken() {
        return infToken;
    }

    public void setInfToken(String infToken) {
        this.infToken = infToken;
    }
}
