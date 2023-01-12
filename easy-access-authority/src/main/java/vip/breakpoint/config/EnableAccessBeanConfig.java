package vip.breakpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import vip.breakpoint.condition.*;
import vip.breakpoint.handler.AccessLimitHandler;
import vip.breakpoint.interceptor.WebLimitInterceptor;
import vip.breakpoint.kaptcha.EasyKaptchaService;
import vip.breakpoint.kaptcha.EasyKaptchaServiceImpl;
import vip.breakpoint.kaptcha.EasyVerifyCodeController;
import vip.breakpoint.service.*;
import vip.breakpoint.service.impl.*;

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
    @Order
    @Bean
    public AccessLimitService getAccessLimitService(UserStoreService userStoreService,
                                                    ClickLimitService clickLimitService,
                                                    VerifyCodeService verifyCodeService,
                                                    UserUriService userUriService) {
        return new DefaultAccessLimitServiceImpl(userStoreService, clickLimitService,
                verifyCodeService, userUriService);
    }

    @Conditional({UserUriCondition.class})
    @Order
    @Bean
    public UserUriService getUserUriService() {
        return new DefaultUserUriService();
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
    @Order
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
    @Order
    @Bean
    public ClickLimitService getClickLimitService() {
        return new DefaultClickLimitServiceImpl();
    }

    // 验证码服务服务
    @Conditional({VerifyCodeCondition.class})
    @Order
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

}
