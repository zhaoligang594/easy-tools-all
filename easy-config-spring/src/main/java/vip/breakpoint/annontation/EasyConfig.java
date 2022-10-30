package vip.breakpoint.annontation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/29
 * 欢迎关注公众号:代码废柴
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyConfig {

    @AliasFor("key")
    String value() default "";

    @AliasFor("value")
    String key() default "";
}
