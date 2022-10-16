package vip.breakpoint.annotion;

import vip.breakpoint.log.LoggingLevel;

import java.lang.annotation.*;

/**
 * @author :breakpoint/赵立刚
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLogging {
    String[] methods() default {}; //需要打印日志的方法

    String timePattern() default "yyyy-MM-dd HH:mm:ss"; // 日期的格式

    LoggingLevel logLevel() default LoggingLevel.INFO; // 打印日志的级别

    boolean loggingInSystem() default false; // 是否在系统中打印日志
}
