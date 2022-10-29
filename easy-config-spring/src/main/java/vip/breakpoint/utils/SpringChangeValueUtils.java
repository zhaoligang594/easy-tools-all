package vip.breakpoint.utils;

import vip.breakpoint.convertor.ListTypeConvertor;
import vip.breakpoint.convertor.MapTypeConvertor;
import vip.breakpoint.convertor.ObjectTypeConvertor;
import vip.breakpoint.convertor.base.TypeConvertor;
import vip.breakpoint.enums.JavaTypeEnum;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.wrapper.SpringBeanWrapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/29
 * 欢迎关注公众号:代码废柴
 */
public class SpringChangeValueUtils {


    private static final Logger log = WebLogFactory.getLogger(SpringChangeValueUtils.class);


    public static void updateTheBeanValues(String value, Set<SpringBeanWrapper> beanWrappers) {
        for (SpringBeanWrapper beanWrapper : beanWrappers) {
            Class<?> clazz = beanWrapper.getType();
            if (EasyStringUtils.isBlank(value)) {
                // use default value
                value = beanWrapper.getDefaultValue();
            }
            if (EasyStringUtils.isBlank(value)) {
                continue;
            }
            if (null != clazz && JavaTypeUtils.isPrimitiveType(clazz)) {
                TypeConvertor<String, ?> typeConvertor =
                        TypeConvertorUtils.getTypeConvertor(JavaTypeEnum.getByClazz(clazz));
                try {
                    Object targetValue = typeConvertor.doConvert(value);
                    ReflectUtils.setFieldValue2Object(beanWrapper.getValueField(), beanWrapper.getBean(), targetValue);
                } catch (Exception e) {
                    log.error("配置转换发生异常 beanName:{} target value:{}", beanWrapper.getBeanName(), value, e);
                }
            } else {
                try {
                    if (null != clazz) {
                        Object targetValue = null;
                        Class<?> valueType = beanWrapper.getValueType();
                        if (Map.class.isAssignableFrom(clazz)) {
                            targetValue = new MapTypeConvertor<>(valueType).doConvert(value);
                        } else if (List.class.isAssignableFrom(clazz)) {
                            targetValue = new ListTypeConvertor<>(valueType).doConvert(value);
                        } else {
                            targetValue = new ObjectTypeConvertor<>(clazz).doConvert(value);
                        }
                        ReflectUtils.setFieldValue2Object(beanWrapper.getValueField(), beanWrapper.getBean(), targetValue);
                    }
                } catch (Exception e) {
                    log.error("配置转换发生异常 beanName:{} target value:{}", beanWrapper.getBeanName(), value, e);
                }
            }
        }
    }
}
