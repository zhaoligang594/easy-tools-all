package vip.breakpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import vip.breakpoint.condition.RestTemplateCondition;
import vip.breakpoint.controller.ConfigChangeController;
import vip.breakpoint.monitor.EasyMonitorController;
import vip.breakpoint.remote.RemoteService;
import vip.breakpoint.remote.impl.RemoteServiceImpl;
import vip.breakpoint.service.LocalConfigService;
import vip.breakpoint.service.RemoteConfigService;
import vip.breakpoint.service.impl.LocalConfigServiceImpl;
import vip.breakpoint.service.impl.RemoteConfigServiceImpl;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
@Configuration
public class EnableAutoRemoteConfig {


    // remote app monitor
    @Bean
    public EasyMonitorController getEasyMonitorController() {
        return new EasyMonitorController();
    }

    @Bean
    public ConfigChangeController getConfigChangeController() {
        return new ConfigChangeController();
    }

    @Conditional({RestTemplateCondition.class})
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RemoteConfigService getRemoteConfigService(LocalConfigService localConfigService) {
        return new RemoteConfigServiceImpl(localConfigService);
    }

    @Bean
    public LocalConfigService getLocalConfigService() {
        return new LocalConfigServiceImpl();
    }

    @Bean
    public RemoteService getRemoteService(LocalConfigService localConfigService) {
        return new RemoteServiceImpl(localConfigService);
    }
}
