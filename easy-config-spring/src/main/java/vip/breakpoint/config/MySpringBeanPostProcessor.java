package vip.breakpoint.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import vip.breakpoint.annontation.EasyConfig;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.ReflectUtils;
import vip.breakpoint.wrapper.SpringBeanWrapper;
import vip.breakpoint.wrapper.SpringBeanWrapperPool;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 记录每一个bean的值的情况
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class MySpringBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = WebLogFactory.getLogger(MySpringBeanPostProcessor.class);

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
            Value valueAnn = field.getAnnotation(Value.class);
            if (null != valueAnn) {
                String valueKey = getRealKey(valueAnn.value());
                if (EasyStringUtils.isNotBlank(valueKey)) {
                    log.info("the bean:{} added the SpringBeanWrapperPool and monitor it", beanName);
                    SpringBeanWrapper wrapper = new SpringBeanWrapper(bean, beanName, valueKey, field.getType(), field);
                    SpringBeanWrapperPool.addSpringBeanWrapper(valueKey, wrapper);
                }
            }
            EasyConfig easyConfigAnn = field.getAnnotation(EasyConfig.class);
            if (null != easyConfigAnn) {
                String key = easyConfigAnn.value();
                if (EasyStringUtils.isBlank(key)) {
                    key = easyConfigAnn.key();
                }
                String valueKey = getRealKey(key);
                if (EasyStringUtils.isNotBlank(valueKey)) {
                    log.info("the bean:{} added the SpringBeanWrapperPool and monitor it", beanName);
                    SpringBeanWrapper wrapper = new SpringBeanWrapper(bean, beanName, valueKey, field.getType(), field);
                    wrapper.setValueType(easyConfigAnn.valueClass());
                    SpringBeanWrapperPool.addSpringBeanWrapper2BackUp(valueKey, wrapper);
                }
            }
        }
    }

    private String getRealKey(String valueKey) {
        if ("${}".endsWith(valueKey)) return "";
        if (valueKey.startsWith("${") && valueKey.endsWith("}")) {
            return valueKey.substring(2, valueKey.length() - 1);
        }
        return valueKey;
    }
}
