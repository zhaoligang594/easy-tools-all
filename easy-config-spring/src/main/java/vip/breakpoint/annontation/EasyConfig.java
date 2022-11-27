package vip.breakpoint.annontation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 自定义动态配置值
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/29
 * 欢迎关注公众号:代码废柴
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyConfig {

    /**
     * 配置值  key:defaultValue
     * the key is from the  *.properties or *.yml or [key.json] file
     *
     * @return the config key and default value
     */
    @AliasFor("key")
    String value() default "";

    // 与 value()相同
    @AliasFor("value")
    String key() default "";

    // 是否是静态不变的值 默认是 跟着 文件的变化而变化
    boolean isStatic() default false;
}
