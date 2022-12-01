package vip.breakpoint.annotion;

import org.springframework.context.annotation.Import;
import vip.breakpoint.config.LoggingBeanDefinitionRegistrar;

import java.lang.annotation.*;

/**
 * 开启项目的日志功能
 *
 * @author :breakpoint/赵立刚
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LoggingBeanDefinitionRegistrar.class})
public @interface EnableEasyLogging {

    /**
     * 是否在应用中打印日志
     *
     * @return true or false
     */
    boolean enableLogInApp() default true;
}
