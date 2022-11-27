package vip.breakpoint.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import vip.breakpoint.annontation.EnableAutoConfig;

import java.util.ArrayList;
import java.util.Arrays;

import static vip.breakpoint.config.AnnConfig.CLASSPATH_FILES_KEY;
import static vip.breakpoint.config.AnnConfig.FILE_SYSTEM_PATHS_KEY;

/**
 * @author : breakpoint
 * create on 2022/10/28
 * 欢迎关注公众号 《代码废柴》
 */
public class EnableAutoConfigBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(EnableSpringAutoConfig.class.getName())) {
            registry.registerBeanDefinition(EnableSpringAutoConfig.class.getName(),
                    new RootBeanDefinition(EnableSpringAutoConfig.class));
        }
        //get the ann type values
        Class<EnableAutoConfig> annType = EnableAutoConfig.class;
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(annType.getName(),
                        false));
        // get the config
        if (null != attributes) {
            String[] fileSystemPaths = attributes.getStringArray(FILE_SYSTEM_PATHS_KEY);
            String[] classpathFiles = attributes.getStringArray(CLASSPATH_FILES_KEY);
            AnnConfig.fileSystemPaths.addAll(new ArrayList<>(Arrays.asList(fileSystemPaths)));
            AnnConfig.classpathFiles.addAll(new ArrayList<>(Arrays.asList(classpathFiles)));
        }
    }
}
