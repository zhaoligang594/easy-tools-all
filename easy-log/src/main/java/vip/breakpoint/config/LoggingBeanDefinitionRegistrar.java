package vip.breakpoint.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import vip.breakpoint.XmlEnableLoggingConfiguration;
import vip.breakpoint.annotion.EnableEasyLogging;

import static vip.breakpoint.config.ConfigCenter.ENABLE_LOG_IN_APP_KEY;

/**
 * bean的注册类
 *
 * @author :breakpoint/赵立刚
 */
public class LoggingBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    // 定义自己的组件
    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition(XmlEnableLoggingConfiguration.class.getName(),
                new RootBeanDefinition(XmlEnableLoggingConfiguration.class));
        //get the ann type values
        Class<EnableEasyLogging> annType = EnableEasyLogging.class;
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(annType.getName(),
                        false));
        if (null != attributes) {
            // 是否开启项目中的日志的能力
            boolean enableLogInApp = attributes.getBoolean(ENABLE_LOG_IN_APP_KEY);
            ConfigCenter.addConfig(ENABLE_LOG_IN_APP_KEY, enableLogInApp);
        }
    }
}
