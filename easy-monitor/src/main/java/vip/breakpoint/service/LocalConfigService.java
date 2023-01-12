package vip.breakpoint.service;

import vip.breakpoint.bean.RemoteConfigBean;
import vip.breakpoint.remote.bean.RemoteConfigVo;

import java.util.List;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
public interface LocalConfigService extends ConfigService {


    List<RemoteConfigBean> getLocalConfigBeanList();

    //添加远端的配置
    void addOnlineRemoteConfigBeans(List<RemoteConfigBean> remoteConfigBeanList);

    /**
     * 获取到配置
     *
     * @param configKey 配置key
     * @return 返回值
     */
    RemoteConfigVo getRemoteConfigVo(String configKey);

    /**
     * 获取到配置
     *
     * @param configKeys 配置key
     * @return 返回配置
     */
    List<RemoteConfigVo> getRemoteConfigVo(String[] configKeys);

}
