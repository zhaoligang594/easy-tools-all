package vip.breakpoint.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口防刷限流注解方式
 *
 * @author :breakpoint/赵立刚
 * create on  2018/04/02
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 两次点击间隔时间  默认时间1000ms  单位ms
     *
     * @return 间隔时间
     */
    long interval() default 1000L;

    /**
     * 是否启用限流防刷的操作
     *
     * @return 是否启动限流
     */
    boolean enableClickLimit() default false;

    /**
     * 接口访问是否需要进行登录
     *
     * @return 访问接口是否需要登录
     */
    boolean isLogIn() default true;

    /**
     * 该接口是否可用
     * 设置成 false 不可以进行访问
     *
     * @return 接口是否可用
     */
    boolean enable() default true;

    /**
     * 验证码是否需要进行验证
     *
     * @return 验证码是否验证
     */
    boolean isVerifyCode() default false;
}
