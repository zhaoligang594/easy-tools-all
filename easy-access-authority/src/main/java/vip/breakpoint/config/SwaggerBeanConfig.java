package vip.breakpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.breakpoint.swagger.config.SwaggerConfigBeanPostProcess;

/**
 * 权限控制的配置类
 *
 * @author : breakpoint
 * create on 2022/10/16
 * 欢迎关注公众号 《代码废柴》
 */
@Configuration
public class SwaggerBeanConfig {

    @Bean
    public SwaggerConfigBeanPostProcess getSwaggerConfigBeanPostProcess() {
        return new SwaggerConfigBeanPostProcess();
    }
}
