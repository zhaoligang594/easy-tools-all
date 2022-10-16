package vip.breakpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import vip.breakpoint.condition.AccessLimitCondition;
import vip.breakpoint.interceptor.AccessLimitInterceptor;
import vip.breakpoint.service.AccessLimitService;
import vip.breakpoint.service.impl.DefaultAccessLimitServiceImpl;

/**
 * @author : breakpoint
 * create on 2022/10/16
 * 欢迎关注公众号 《代码废柴》
 */
@Configuration
public class EnableAccessBeanConfig {

    /**
     * 获取操作限制的业务服务
     */
    @Conditional({AccessLimitCondition.class})
    @Bean
    public AccessLimitService getAccessLimitService() {
        return new DefaultAccessLimitServiceImpl();
    }

    /**
     * 用户请求的拦截器对象
     */
    @Bean
    public AccessLimitInterceptor getAccessLimitInterceptor(AccessLimitService accessLimitService) {
        return new AccessLimitInterceptor(accessLimitService);
    }
}
