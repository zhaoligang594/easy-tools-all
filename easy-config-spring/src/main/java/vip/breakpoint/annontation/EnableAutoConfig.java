package vip.breakpoint.annontation;

import org.springframework.context.annotation.Import;
import vip.breakpoint.config.EnableAutoConfigBeanDefinitionRegistrar;

import java.lang.annotation.*;

/**
 * 启动自动更新的配置
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EnableAutoConfigBeanDefinitionRegistrar.class})
public @interface EnableAutoConfig {
}
