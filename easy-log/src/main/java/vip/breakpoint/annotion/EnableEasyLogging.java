package vip.breakpoint.annotion;

import org.springframework.context.annotation.Import;
import vip.breakpoint.config.LoggingBeanDefinitionRegistrar;

import java.lang.annotation.*;

/**
 * @author :breakpoint/赵立刚
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LoggingBeanDefinitionRegistrar.class})
public @interface EnableEasyLogging {
}
