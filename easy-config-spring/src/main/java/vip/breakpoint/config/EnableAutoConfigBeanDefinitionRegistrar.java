package vip.breakpoint.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * @author : breakpoint
 * create on 2022/10/28
 * 欢迎关注公众号 《代码废柴》
 */
public class EnableAutoConfigBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition(EnableSpringAutoConfig.class.getName(),
                new RootBeanDefinition(EnableSpringAutoConfig.class));
    }
}
