package vip.breakpoint.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录每一个bean的值的情况
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class MySpringBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = WebLogFactory.getLogger(MySpringBeanPostProcessor.class);

    private final Map<String, Object> key2BeanMap = new ConcurrentHashMap<>();

    @Nullable
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName)
            throws BeansException {
        log.info("beanName:{}", beanName);
        Class<?> aClass = bean.getClass();
        return bean;
    }

}
