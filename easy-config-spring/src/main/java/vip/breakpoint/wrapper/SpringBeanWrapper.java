package vip.breakpoint.wrapper;

import java.lang.reflect.Field;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class SpringBeanWrapper {

    private Object bean;

    private String beanName;

    private String valueKey;

    private Class<?> valueType;

    private Field valueField;

    public SpringBeanWrapper(Object bean, String beanName, String valueKey, Class<?> valueType, Field valueField) {
        this.bean = bean;
        this.beanName = beanName;
        this.valueKey = valueKey;
        this.valueType = valueType;
        this.valueField = valueField;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public Class<?> getValueType() {
        return valueType;
    }

    public void setValueType(Class<?> valueType) {
        this.valueType = valueType;
    }

    public Field getValueField() {
        return valueField;
    }

    public void setValueField(Field valueField) {
        this.valueField = valueField;
    }
}
