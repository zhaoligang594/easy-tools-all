package vip.breakpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.breakpoint.process.LoggingBeanPostProcessor;

/**
 * @author 赵立刚
 * Created on 2021-04-02
 */
@Configuration
public class WebLoggingAutoConfiguration {

    @Bean
    public static LoggingBeanPostProcessor loggingBeanPostProcessor() {
        return new LoggingBeanPostProcessor();
    }
}
