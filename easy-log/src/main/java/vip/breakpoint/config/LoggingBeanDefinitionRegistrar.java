package vip.breakpoint.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;
import vip.breakpoint.XmlEnableLoggingConfiguration;

/**
 * bean的注册类
 *
 * @author :breakpoint/赵立刚
 */
public class LoggingBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    // 定义自己的组件
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {

        /*
         * 下面的功能有待于扩展
         */
        MultiValueMap<String, Object> attributes =
                importingClassMetadata.getAllAnnotationAttributes("vip.breakpoint.annotion.EnableLoggingConfiguration");
        // 获取线程池的大小
        int poolSize = (int) attributes.get("poolSize").get(0);
        registry.registerBeanDefinition(XmlEnableLoggingConfiguration.class.getName(),
                new RootBeanDefinition(XmlEnableLoggingConfiguration.class));
    }
}
