package vip.breakpoint.annotation;

import java.lang.annotation.*;

/**
 * 别名
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/28
 * 欢迎关注公众号:代码废柴
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AliasFor {
    String value() default "";
}
