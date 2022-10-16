package vip.breakpoint.annotation;

import org.springframework.context.annotation.Import;
import vip.breakpoint.config.EnableAccessBeanDefinitionRegistrar;
import vip.breakpoint.config.WebConfigAdapter;

import java.lang.annotation.*;

/**
 * 开启拦截功能
 *
 * @author : breakpoint
 * create on 2022/10/16
 * 欢迎关注公众号 《代码废柴》
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EnableAccessBeanDefinitionRegistrar.class, WebConfigAdapter.class})
public @interface EnableAccessLimit {
}
