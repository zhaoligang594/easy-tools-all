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
    long interval() default 1000L; // 两次点击间隔时间  默认时间1000ms  单位ms

    boolean isEnableClickLimit() default true; // 是否启用限流防刷的操作

    boolean isLogIn() default true; // 是否需要进行登录

    boolean freezeUserCanAccess() default false; // 冻结的账户是否可以进行通过   必须是 isLogIn 为true的情况下才有效

    boolean enable() default true; // 该接口是否可用  也可以说接口是否可达

    boolean isVerifyCode() default false; // 验证码是否需要进行验证
}
