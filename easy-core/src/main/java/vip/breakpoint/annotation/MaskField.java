package vip.breakpoint.annotation;

import vip.breakpoint.enums.MaskTypeEnum;

import java.lang.annotation.*;

/**
 * mask field
 * tag there have to mask field value
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/31
 * 欢迎关注公众号:代码废柴
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MaskField {
    // mask 值的 类型
    MaskTypeEnum value() default MaskTypeEnum.NONE;
}
