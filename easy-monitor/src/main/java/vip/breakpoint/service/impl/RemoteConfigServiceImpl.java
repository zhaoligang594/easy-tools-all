package vip.breakpoint.service.impl;

import vip.breakpoint.annontation.EasyConfig;
import vip.breakpoint.bean.RemoteConfigBean;
import vip.breakpoint.config.RemoteConfigCenter;
import vip.breakpoint.remote.bean.RemoteConfigVo;
import vip.breakpoint.service.LocalConfigService;
import vip.breakpoint.service.RemoteConfigService;
import vip.breakpoint.service.base.BaseRemoteService;
import vip.breakpoint.utils.EasyColUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
public class RemoteConfigServiceImpl extends BaseRemoteService implements RemoteConfigService {

    @EasyConfig("config.center.host:120.0.0.1")
    private String configCenterHost;
    @EasyConfig("config.center.port:8080")
    private Integer configCenterPort;
    @EasyConfig("config.center.token:admin")
    private String configCenterInfToken;

    private final LocalConfigService localConfigService;

    public RemoteConfigServiceImpl(LocalConfigService localConfigService) {
        this.localConfigService = localConfigService;
    }


    @Override
    public List<RemoteConfigBean> getRemoteConfigBeanList() {
        String realUri = getRealUri(configCenterHost, configCenterPort, CONFIG_CENTER_REMOTE_HOST_URI);
        return getListFromRemote(realUri, new HashMap<>(), RemoteConfigBean.class);
    }

    @Override
    public List<RemoteConfigVo> getRemoteConfigVoList(List<RemoteConfigBean> remoteConfigBeanList) {
        return null;
    }

    @Override
    public RemoteConfigVo getNewestConfigByConfigKey(String configKey) {
        List<RemoteConfigBean> localConfigBeanList = localConfigService.getLocalConfigBeanList();
        if (EasyColUtils.isNotEmpty(localConfigBeanList)) {
            String realUri = getRealUri(configCenterHost, configCenterPort, CONFIG_CENTER_CONFIG_VALUE_URI);
            HashMap<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("configKey", configKey);
            for (int i = 0; i < localConfigBeanList.size(); i++) {
                RemoteConfigVo remoteConfigVo =
                        getObjectFromRemote(realUri, uriVariables, RemoteConfigVo.class);
                if (null != remoteConfigVo) {
                    return remoteConfigVo;
                }
            }
        }
        return null;
    }

    @Override
    public Boolean registerClient(RemoteConfigBean remoteConfigBea) {
        RemoteConfigCenter.addRemoteConfigBean(remoteConfigBea);
        return true;
    }
}
