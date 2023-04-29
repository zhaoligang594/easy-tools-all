package vip.breakpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import vip.breakpoint.condition.LoggingBPPCondition;
import vip.breakpoint.process.LoggingBeanPostProcessor;

/**
 * @author 赵立刚
 * Created on 2021-04-02
 */
@Configuration
public class WebLoggingAutoConfiguration {

    @Conditional(LoggingBPPCondition.class)
    @Bean("loggingBeanPostProcessor")
    public static LoggingBeanPostProcessor loggingBeanPostProcessor() {
        return new LoggingBeanPostProcessor();
    }
}
