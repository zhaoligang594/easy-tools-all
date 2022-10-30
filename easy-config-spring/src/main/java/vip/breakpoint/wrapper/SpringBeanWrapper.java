package vip.breakpoint.wrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class SpringBeanWrapper {
    /**
     * 对象
     */
    private Object bean;

    /**
     * name
     */
    private String beanName;

    /**
     * key
     */
    private String valueKey;

    /**
     * type
     */
    private Class<?> type;

    /**
     * field
     */
    private Field valueField;

    /**
     * 复杂类型的值的类型
     */
    private Type valueType;

    /**
     * 默认值
     */
    private String defaultValue;

    public SpringBeanWrapper(Object bean, String beanName, String valueKey, Class<?> type, Field valueField) {
        this.bean = bean;
        this.beanName = beanName;
        this.valueKey = valueKey;
        this.type = type;
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

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Field getValueField() {
        return valueField;
    }

    public void setValueField(Field valueField) {
        this.valueField = valueField;
    }

    public Type getValueType() {
        return valueType;
    }

    public void setValueType(Type valueType) {
        this.valueType = valueType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpringBeanWrapper wrapper = (SpringBeanWrapper) o;
        return Objects.equals(bean, wrapper.bean) && Objects.equals(beanName, wrapper.beanName)
                && Objects.equals(valueKey, wrapper.valueKey) && Objects.equals(type, wrapper.type) && Objects.equals(valueField, wrapper.valueField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bean, beanName, valueKey, type, valueField);
    }
}
