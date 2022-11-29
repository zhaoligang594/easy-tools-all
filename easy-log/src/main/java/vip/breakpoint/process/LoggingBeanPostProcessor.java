package vip.breakpoint.process;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import vip.breakpoint.annotion.EasyLog;
import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.loghandle.EasyLoggingHandle;
import vip.breakpoint.factory.LoggingFactory;
import vip.breakpoint.utils.EasyColUtils;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.ReflectUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 收集所有注解的方法
 *
 * @author :breakpoint/赵立刚
 */
public class LoggingBeanPostProcessor implements BeanDefinitionRegistryPostProcessor,
        BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private BeanDefinitionRegistry registry;
    private ConfigurableListableBeanFactory beanFactory;
    private final Set<String> beanNamesSet = new HashSet<String>();

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName)
            throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName)
            throws BeansException {
        if (beanNamesSet.contains(beanName)) {
            Class<?> oriClass = bean.getClass();
            ObjectMethodDefinition methodDefinition = new ObjectMethodDefinition();
            this.setMethodDefinition(oriClass, methodDefinition);
            // 是否需要被进行代理
            if (methodDefinition.isShouldProxy()) {
                EasyLoggingHandle easyLoggingHandle;
                try {
                    easyLoggingHandle = applicationContext.getBean(EasyLoggingHandle.class);
                } catch (BeansException e) {
                    easyLoggingHandle = null;
                }
                Object loggingProxyObject =
                        LoggingFactory.getLoggingCGLibProxyObject(applicationContext.getClassLoader(),
                                methodDefinition, bean, oriClass, easyLoggingHandle);
                if (null != loggingProxyObject) {
                    return loggingProxyObject;
                }
            }
        }
        return bean;
    }

    private void setMethodDefinition(Class<?> clazz, ObjectMethodDefinition methodDefinition) {
        if (clazz != Object.class) {
            Map<Method, EasyLog> method2AnnMap = new HashMap<>();
            // 获取到所有的方法
            List<Method> methods = ReflectUtils.getMethodsFromClazz(clazz);
            if (EasyColUtils.isNotEmpty(methods)) {
                for (Method method : methods) {
                    EasyLog annotation = method.getAnnotation(EasyLog.class);
                    if (null != annotation) {
                        method2AnnMap.put(method, annotation);
                    }
                }
            }
            if (!method2AnnMap.isEmpty()) {
                methodDefinition.addCandidateMethods(method2AnnMap);
            }
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> klass : interfaces) {
                this.setMethodDefinition(klass, methodDefinition);
            }
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        // 收集所有用户自定义的bean
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        beanNamesSet.addAll(Arrays.asList(beanDefinitionNames));
    }
}
