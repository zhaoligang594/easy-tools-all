package vip.breakpoint.remote.impl;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import vip.breakpoint.enums.ChangeTypeEnum;
import vip.breakpoint.listener.ConfigChangeListener;
import vip.breakpoint.listener.ConfigChangeListenerPool;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.remote.ConfigChangeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/27
 * 欢迎关注公众号:代码废柴
 */
public class ConfigChangeServiceImpl implements ConfigChangeService {

    private final ThreadPoolTaskExecutor executor;
    private final ConfigChangeListenerPool configChangeListenerPool;

    private static final Logger log = WebLogFactory.getLogger(ConfigChangeServiceImpl.class);


    public ConfigChangeServiceImpl(ThreadPoolTaskExecutor executor,
                                   ConfigChangeListenerPool configChangeListenerPool) {
        this.executor = executor;
        this.configChangeListenerPool = configChangeListenerPool;
    }


    @Override
    public boolean doChangeConfig(final String configKey, final String configValue) {
        try {
            log.info("the remote config value have change!!! configKey:{} , configValue:{} ", configKey, configValue);
            List<ConfigChangeListener> configChangeListeners = configChangeListenerPool.getConfigChangeListeners();
            if (null != configChangeListeners) {
                for (ConfigChangeListener configChangeListener : configChangeListeners) {
                    Map<String, String> key2ValueMap = new HashMap<>();
                    key2ValueMap.put(configKey, configValue);
                    configChangeListener.doChangedConfigFileRefresh(ChangeTypeEnum.REMOTE, null, key2ValueMap);
                }
            }
            return true;
        } catch (Exception e) {
            log.error("change the config value occur error configKey:{} , configValue:{} ",
                    configKey, configValue, e);
            return false;
        }
    }
}
