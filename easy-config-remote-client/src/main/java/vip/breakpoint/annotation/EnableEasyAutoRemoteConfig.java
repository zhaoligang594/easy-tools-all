package vip.breakpoint.annotation;

import org.springframework.context.annotation.Import;
import vip.breakpoint.annontation.EnableAutoConfig;
import vip.breakpoint.config.EnableAutoRemoteConfig;

import java.lang.annotation.*;

/**
 * 开启远程更新配置接口
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAccessLimit
@EnableAutoConfig
@Import({EnableAutoRemoteConfig.class})
public @interface EnableEasyAutoRemoteConfig {
}
