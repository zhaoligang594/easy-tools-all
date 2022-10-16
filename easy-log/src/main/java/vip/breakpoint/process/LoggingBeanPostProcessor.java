package vip.breakpoint.process;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import vip.breakpoint.annotion.WebLogging;
import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.loghandle.EasyLoggingHandle;
import vip.breakpoint.factory.LoggingFactory;

import java.util.*;

/**
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
            WebLogging webLogging = clazz.getAnnotation(WebLogging.class);
            if (null != webLogging) {
                methodDefinition.addWebLogging(clazz, webLogging);
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
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        beanNamesSet.addAll(Arrays.asList(beanDefinitionNames));
    }
}
