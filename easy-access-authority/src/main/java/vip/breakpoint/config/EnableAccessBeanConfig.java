package vip.breakpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import vip.breakpoint.condition.AccessLimitCondition;
import vip.breakpoint.condition.ClickCondition;
import vip.breakpoint.condition.UserStoreCondition;
import vip.breakpoint.condition.VerifyCodeCondition;
import vip.breakpoint.handler.AccessLimitHandler;
import vip.breakpoint.interceptor.WebLimitInterceptor;
import vip.breakpoint.kaptcha.EasyKaptchaService;
import vip.breakpoint.kaptcha.EasyKaptchaServiceImpl;
import vip.breakpoint.kaptcha.EasyVerifyCodeController;
import vip.breakpoint.monitor.EasyMonitorController;
import vip.breakpoint.remote.ConfigChangeController;
import vip.breakpoint.service.AccessLimitService;
import vip.breakpoint.service.ClickLimitService;
import vip.breakpoint.service.UserStoreService;
import vip.breakpoint.service.VerifyCodeService;
import vip.breakpoint.service.impl.DefaultAccessLimitServiceImpl;
import vip.breakpoint.service.impl.DefaultClickLimitServiceImpl;
import vip.breakpoint.service.impl.DefaultUserStoreServiceImpl;
import vip.breakpoint.service.impl.DefaultVerifyCodeServiceImpl;

import java.util.ArrayList;

/**
 * 权限控制的配置类
 *
 * @author : breakpoint
 * create on 2022/10/16
 * 欢迎关注公众号 《代码废柴》
 */
@Configuration
public class EnableAccessBeanConfig {

    // 获取操作限制的业务服务
    @Conditional({AccessLimitCondition.class})
    @Bean
    public AccessLimitService getAccessLimitService(UserStoreService userStoreService,
                                                    ClickLimitService clickLimitService,
                                                    VerifyCodeService verifyCodeService) {
        return new DefaultAccessLimitServiceImpl(userStoreService, clickLimitService, verifyCodeService);
    }

    // 用户请求的拦截器对象
    @Bean
    public WebLimitInterceptor getAccessLimitInterceptor(AccessLimitHandler handler) {
        WebLimitInterceptor webLimitInterceptor = new WebLimitInterceptor();
        webLimitInterceptor.setHandler(handler);
        webLimitInterceptor.addIgnorePaths(new ArrayList<>(AnnConfig.ignorePaths));
        return webLimitInterceptor;
    }

    // 用户存储服务
    @Conditional({UserStoreCondition.class})
    @Bean
    public UserStoreService getUserStoreService() {
        return new DefaultUserStoreServiceImpl();
    }


    // 访问控制处理类
    @Bean
    public AccessLimitHandler getAccessLimitInterceptorHandler(AccessLimitService accessLimitService) {
        AccessLimitHandler handler = new AccessLimitHandler();
        handler.setAccessLimitService(accessLimitService);
        return handler;
    }

    // 点击限制服务
    @Conditional({ClickCondition.class})
    @Bean
    public ClickLimitService getClickLimitService() {
        return new DefaultClickLimitServiceImpl();
    }

    // 验证码服务服务
    @Conditional({VerifyCodeCondition.class})
    @Bean
    public VerifyCodeService getVerifyCodeService() {
        return new DefaultVerifyCodeServiceImpl();
    }


    @Bean
    public EasyVerifyCodeController getEasyVerifyCodeController() {
        return new EasyVerifyCodeController();
    }

    @Bean
    public EasyKaptchaService getEasyKaptchaService() {
        return new EasyKaptchaServiceImpl();
    }

    @Bean
    public SystemConfigController getSystemConfigController() {
        return new SystemConfigController();
    }

    @Bean
    public ConfigChangeController getConfigChangeController() {
        return new ConfigChangeController();
    }

    @Bean
    public EasyMonitorController getEasyMonitorController() {
        return new EasyMonitorController();
    }
}
