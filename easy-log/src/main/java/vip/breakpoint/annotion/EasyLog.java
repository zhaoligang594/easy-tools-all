package vip.breakpoint.annotion;

import java.lang.annotation.*;

/**
 * @author :breakpoint/赵立刚
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyLog {

    /**
     * 名字 默认选取方法的名字
     *
     * @return name
     */
    String name() default ""; //

    /**
     * 打印日志的时间格式
     *
     * @return pattern
     */
    String timePattern() default "yyyy-MM-dd HH:mm:ss"; // 日期的格式

    /**
     * 是否在系统中打印日志
     *
     * @return true or false
     */
    boolean logInApp() default true;
}
