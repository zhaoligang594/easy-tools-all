package vip.breakpoint.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;
import vip.breakpoint.annotation.EnableAccessLimit;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author : breakpoint
 * create on 2022/10/16
 * 欢迎关注公众号 《代码废柴》
 */
public class EnableAccessAnnBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    // from the EnableAccessLimit
    private static final String ENABLE_SWAGGER_ANN_KEY = "enableSwagger";
    private static final String ENABLE_DATE_PARAM_PARSER_ANN_KEY = "enableDateParamParser";
    private static final String CONFIG_FILE_SYSTEM_PATHS_KEY = "configFileSystemPaths";
    private static final String IGNORE_PATHS_KEY = "ignorePaths";

    // 定义自己的组件
    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
                                        @NonNull BeanDefinitionRegistry registry) {
        //get the ann type values
        Class<EnableAccessLimit> annType = EnableAccessLimit.class;
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(annType.getName(),
                        false));
        boolean enableTheSwagger = getConfigValue(attributes, ENABLE_SWAGGER_ANN_KEY);
        if (enableTheSwagger) {
            registry.registerBeanDefinition(Swagger2DocumentationConfiguration.class.getName(),
                    new RootBeanDefinition(Swagger2DocumentationConfiguration.class));
            registry.registerBeanDefinition(SwaggerBeanConfig.class.getName(),
                    new RootBeanDefinition(SwaggerBeanConfig.class));
        }
        boolean enableParseDate = getConfigValue(attributes, ENABLE_DATE_PARAM_PARSER_ANN_KEY);
        if (enableParseDate) {
            registry.registerBeanDefinition(FormattingConversionServiceBeanPostProcess.class.getName(),
                    new RootBeanDefinition(FormattingConversionServiceBeanPostProcess.class));
        }
        if (null != attributes) {
            String[] fileSystemPaths = attributes.getStringArray(CONFIG_FILE_SYSTEM_PATHS_KEY);
            String[] ignorePaths = attributes.getStringArray(IGNORE_PATHS_KEY);
            AnnConfig.fileSystemPaths.addAll(new ArrayList<>(Arrays.asList(fileSystemPaths)));
            AnnConfig.ignorePaths.addAll(new ArrayList<>(Arrays.asList(ignorePaths)));
        }
    }

    private boolean getConfigValue(@Nullable AnnotationAttributes attributes, @NonNull String annKey) {
        boolean enableTheSwagger = false;
        if (null != attributes) {
            enableTheSwagger = attributes.getBoolean(annKey);
        }
        return enableTheSwagger;
    }
}
