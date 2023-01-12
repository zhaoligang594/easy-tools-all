package vip.breakpoint.service;

import vip.breakpoint.bean.RemoteConfigBean;
import vip.breakpoint.remote.bean.RemoteConfigVo;

import java.util.List;

/**
 * 远端配置服务
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
public interface RemoteConfigService extends ConfigService {

    // 获取到可以提供配置的服务
    List<RemoteConfigBean> getRemoteConfigBeanList();

    /**
     * 获取远端的配置的信息
     *
     * @param remoteConfigBeanList 可以提供服务的配置中心
     * @return 返回所有的配置
     */
    List<RemoteConfigVo> getRemoteConfigVoList(List<RemoteConfigBean> remoteConfigBeanList);

    /**
     * 获取到最新的配置
     *
     * @param configKey 配置的key
     * @return 获取到最新的配置
     */
    RemoteConfigVo getNewestConfigByConfigKey(String configKey);

    /**
     * 注册客户端
     *
     * @param remoteConfigBean 客户端信息
     * @return 返回值
     */
    Boolean registerClient(RemoteConfigBean remoteConfigBean);
}
