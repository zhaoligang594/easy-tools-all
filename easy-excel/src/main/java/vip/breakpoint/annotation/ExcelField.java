package vip.breakpoint.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel 的工具注解
 *
 * @author breakpoint/赵先生
 * 2020/10/28
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    @AliasFor("name")
    String value() default "";

    // 和 属性 的 汉语意思
    String name() default "";

    // 日期的类型
    String datePattern() default "yyyy-MM-dd HH:mm:ss";

    // 下拉列表的操作里面的 可以填写的 值
    String[] selectValues() default {};

    //  排序 order
    int order() default 0;

    // 是否导出该列
    boolean isExplored() default true;

    // 模版是否包含这一列
    boolean isMouldColumn() default true;
}