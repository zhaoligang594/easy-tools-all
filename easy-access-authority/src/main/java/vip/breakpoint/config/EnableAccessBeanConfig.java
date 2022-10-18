package vip.breakpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import vip.breakpoint.condition.AccessLimitCondition;
import vip.breakpoint.condition.ClickCondition;
import vip.breakpoint.condition.UserStoreCondition;
import vip.breakpoint.handler.AccessLimitHandler;
import vip.breakpoint.interceptor.WebLimitInterceptor;
import vip.breakpoint.service.AccessLimitService;
import vip.breakpoint.service.ClickLimitService;
import vip.breakpoint.service.UserStoreService;
import vip.breakpoint.service.impl.DefaultAccessLimitServiceImpl;
import vip.breakpoint.service.impl.DefaultClickLimitServiceImpl;
import vip.breakpoint.service.impl.DefaultUserStoreServiceImpl;

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
    public AccessLimitService getAccessLimitService(UserStoreService userStoreService,
                                                    ClickLimitService clickLimitService) {
        return new DefaultAccessLimitServiceImpl(userStoreService, clickLimitService);
    }

    /**
     * 用户请求的拦截器对象
     */
    @Bean
    public WebLimitInterceptor getAccessLimitInterceptor(AccessLimitHandler handler) {
        WebLimitInterceptor webLimitInterceptor = new WebLimitInterceptor();
        webLimitInterceptor.setHandler(handler);
        return webLimitInterceptor;
    }

    @Conditional({UserStoreCondition.class})
    @Bean
    public UserStoreService getUserStoreService() {
        return new DefaultUserStoreServiceImpl();
    }


    @Bean
    public AccessLimitHandler getAccessLimitInterceptorHandler(AccessLimitService accessLimitService) {
        AccessLimitHandler handler = new AccessLimitHandler();
        handler.setAccessLimitService(accessLimitService);
        return handler;
    }

    @Conditional({ClickCondition.class})
    @Bean
    public ClickLimitService getClickLimitService() {
        return new DefaultClickLimitServiceImpl();
    }

}
