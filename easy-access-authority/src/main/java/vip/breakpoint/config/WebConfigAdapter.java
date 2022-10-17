package vip.breakpoint.config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import vip.breakpoint.condition.WebConfigCondition;
import vip.breakpoint.interceptor.WebLimitInterceptor;
import vip.breakpoint.resolver.PageInfoResolver;

import javax.annotation.Resource;
import java.util.List;

/**
 * 拦截配置
 *
 * @author breakpoint/赵先生
 * 2020/12/24
 */
@Conditional({WebConfigCondition.class})
@EnableWebMvc
@Configuration
public class WebConfigAdapter extends WebMvcConfigurerAdapter {

    @Resource
    private WebLimitInterceptor webLimitInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(webLimitInterceptor);
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath*:/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath*:/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath*:/js/");
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath*:/static/swagger/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        // 分页参数请求处理器
        argumentResolvers.add(new PageInfoResolver());
    }
}
