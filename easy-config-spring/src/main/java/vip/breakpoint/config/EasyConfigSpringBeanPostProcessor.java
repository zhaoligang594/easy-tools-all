package vip.breakpoint.config;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import vip.breakpoint.annontation.EasyConfig;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.AopTargetUtils;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.JavaTypeUtils;
import vip.breakpoint.utils.ReflectUtils;
import vip.breakpoint.wrapper.SpringBeanWrapper;
import vip.breakpoint.wrapper.SpringBeanWrapperPool;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 记录每一个bean的值的情况
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class EasyConfigSpringBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = WebLogFactory.getLogger(EasyConfigSpringBeanPostProcessor.class);

    @Nullable
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName)
            throws BeansException {
        processBean(bean, beanName);
        return bean;
    }

    // 增加bean
    private void processBean(@NonNull Object bean, @NonNull String beanName) {
        Class<?> beanClass = bean.getClass();
        List<Field> declaredFields = ReflectUtils.getFieldsFromClazz(beanClass);
        for (Field field : declaredFields) {
            /*  @Value   */
            Value valueAnn = field.getAnnotation(Value.class);
            if (null != valueAnn) {
                String value2DefaultValue = getRealKey(valueAnn.value());
                SpringBeanWrapper beanWrapper = getBeanWrapper(value2DefaultValue,
                        bean, beanName, field, false);
                if (null != beanWrapper) {
                    // log.info("the bean:{} added the SpringBeanWrapperPool and monitor it", beanName);
                    SpringBeanWrapperPool.addSpringBeanWrapper(beanWrapper.getValueKey(), beanWrapper);
                    List<SpringBeanWrapper> targetBeanWrappers = getTargetBeanWrappers(beanWrapper);
                    SpringBeanWrapperPool.addSpringBeanWrapper(beanWrapper.getValueKey(),
                            new HashSet<>(targetBeanWrappers));
                }
            }
            /*  @EasyConfig  */
            EasyConfig easyConfigAnn = field.getAnnotation(EasyConfig.class);
            if (null != easyConfigAnn) {
                String key = easyConfigAnn.value();
                if (EasyStringUtils.isBlank(key)) {
                    key = easyConfigAnn.key();
                }
                String value2DefaultValue = getRealKey(key);
                SpringBeanWrapper beanWrapper = getBeanWrapper(value2DefaultValue,
                        bean, beanName, field, easyConfigAnn.isStatic());
                if (null != beanWrapper) {
                    // log.info("the bean:{} added the SpringBeanWrapperPool and monitor it", beanName);
                    SpringBeanWrapperPool.addSpringBeanWrapper2BackUp(beanWrapper.getValueKey(), beanWrapper);
                    List<SpringBeanWrapper> targetBeanWrappers = getTargetBeanWrappers(beanWrapper);
                    SpringBeanWrapperPool.addSpringBeanWrapper2BackUp(beanWrapper.getValueKey(),
                            new HashSet<>(targetBeanWrappers));
                }
            }
        }
    }

    private SpringBeanWrapper getBeanWrapper(String valueKey, Object bean, String beanName,
                                             Field field, boolean isStatic) {
        if (EasyStringUtils.isNotBlank(valueKey)) {
            String[] keyAndDefaultValue = getKeyAndDefaultValue(valueKey);
            valueKey = keyAndDefaultValue[0];
            SpringBeanWrapper wrapper = new SpringBeanWrapper(bean, beanName, valueKey,
                    field.getType(), field, isStatic);
            // wrapper.setValueType(easyConfigAnn.valueClass());
            wrapper.setDefaultValue(keyAndDefaultValue[1]);
            if (!JavaTypeUtils.isPrimitiveType(field.getType())) {
                // this type is not primitive type
                Type[] typeByField = getTypeByField(field);
                wrapper.setValueType(typeByField[1]);
            }
            return wrapper;
        }
        return null;
    }

    private List<SpringBeanWrapper> getTargetBeanWrappers(SpringBeanWrapper obw) {
        List<SpringBeanWrapper> retList = new ArrayList<>();
        if (null != obw) {
            Object bean = obw.getBean();
            if (AopTargetUtils.isProxyByEasyLog(bean)) {
                try {
                    Object target = AopTargetUtils.getTargetFromEasyLogProxy(bean);
                    if (null != target) {
                        SpringBeanWrapper wrapper = new SpringBeanWrapper(target, obw.getBeanName(), obw.getValueKey(),
                                obw.getType(), obw.getValueField(), obw.isStatic());
                        wrapper.setValueType(obw.getValueType());
                        retList.add(wrapper);
                        // spring proxy
                        SpringBeanWrapper targetBeanWrapper = getTargetBeanWrapper(wrapper);
                        if (null != targetBeanWrapper) {
                            retList.add(targetBeanWrapper);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // spring proxy
            SpringBeanWrapper targetBeanWrapper = getTargetBeanWrapper(obw);
            if (null != targetBeanWrapper) {
                retList.add(targetBeanWrapper);
            }
        }
        return retList;
    }

    private SpringBeanWrapper getTargetBeanWrapper(SpringBeanWrapper obw) {
        if (null != obw) {
            Object bean = obw.getBean();
            if (AopUtils.isAopProxy(bean)) {
                try {
                    Object target = AopTargetUtils.getTarget(bean);
                    SpringBeanWrapper wrapper = new SpringBeanWrapper(target, obw.getBeanName(), obw.getValueKey(),
                            obw.getType(), obw.getValueField(), obw.isStatic());
                    wrapper.setValueType(obw.getValueType());
                    return wrapper;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private Type[] getTypeByField(Field field) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (Map.class.isAssignableFrom(field.getType())) {
                if (actualTypeArguments.length > 1) {
                    return new Type[]{field.getType(), actualTypeArguments[1]};
                }
            } else if (List.class.isAssignableFrom(field.getType())) {
                if (actualTypeArguments.length > 0) {
                    return new Type[]{field.getType(), actualTypeArguments[0]};
                }
            } else {
                return new Type[]{field.getType(), Object.class};
            }
            return actualTypeArguments;
        }
        return new Type[]{field.getType(), Object.class};
    }

    private String[] getKeyAndDefaultValue(String valueKey) {
        if (valueKey.contains(":")) {
            int idx = valueKey.indexOf(":");
            return new String[]{valueKey.substring(0, idx), valueKey.substring(idx + 1)};
        }
        return new String[]{valueKey, null};
    }

    private String getRealKey(String valueKey) {
        if ("${}".endsWith(valueKey)) return "";
        if (valueKey.startsWith("${") && valueKey.endsWith("}")) {
            return valueKey.substring(2, valueKey.length() - 1);
        }
        return valueKey;
    }
}
