package vip.breakpoint.service.impl;

import vip.breakpoint.bean.RemoteConfigBean;
import vip.breakpoint.config.RemoteConfigCenter;
import vip.breakpoint.remote.bean.RemoteConfigVo;
import vip.breakpoint.service.LocalConfigService;
import vip.breakpoint.supplier.base.PropertiesContextPool;
import vip.breakpoint.utils.EasyStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
public class LocalConfigServiceImpl implements LocalConfigService {

    @Override
    public List<RemoteConfigBean> getLocalConfigBeanList() {
        // 获取本地的配置
        return RemoteConfigCenter.getLocalRemoteConfigBeanList();
    }

    @Override
    public void addOnlineRemoteConfigBeans(List<RemoteConfigBean> remoteConfigBeanList) {
        RemoteConfigCenter.addOnlineRemoteConfigBeans(remoteConfigBeanList);
    }

    @Override
    public RemoteConfigVo getRemoteConfigVo(String configKey) {
        RemoteConfigVo ret = new RemoteConfigVo();
        ret.setConfigKey(configKey);
        ret.setConfigValue(PropertiesContextPool.getConfigValuesMap().get(configKey));
        return ret;
    }

    @Override
    public List<RemoteConfigVo> getRemoteConfigVo(String[] configKeys) {
        List<RemoteConfigVo> retList = new ArrayList<>();
        if (null != configKeys) {
            for (String configKey : configKeys) {
                if (EasyStringUtils.isNotBlank(configKey)) {
                    retList.add(this.getRemoteConfigVo(configKey.trim()));
                }
            }
        }
        return retList;
    }

}
