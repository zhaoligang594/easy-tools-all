package vip.breakpoint;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import vip.breakpoint.process.LoggingBeanPostProcessor;

/**
 * xml的配置
 *
 * @author :breakpoint/赵立刚
 */
public class XmlEnableLoggingConfiguration implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // nothing to do
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registry.registerBeanDefinition(LoggingBeanPostProcessor.class.getName(), new RootBeanDefinition(LoggingBeanPostProcessor.class));
    }
}
