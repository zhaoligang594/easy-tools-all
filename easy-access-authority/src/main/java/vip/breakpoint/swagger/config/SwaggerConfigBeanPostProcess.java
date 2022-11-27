package vip.breakpoint.swagger.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import vip.breakpoint.swagger.bean.SwaggerConfigBean;
import vip.breakpoint.swagger.bean.SwaggerFactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : breakpoint/赵先生
 * create on 2022/11/05
 * 欢迎关注公众号:代码废柴
 */
public class SwaggerConfigBeanPostProcess implements BeanPostProcessor, ApplicationContextAware {

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        List<SwaggerConfigBean> swaggerInfos = new ArrayList<>();
        String[] beanNamesForType = applicationContext.getBeanNamesForType(SwaggerConfigInfo.class);
        if (beanNamesForType.length > 0) {
            for (String beanName : beanNamesForType) {
                SwaggerConfigInfo bean = applicationContext.getBean(beanName, SwaggerConfigInfo.class);
                swaggerInfos.addAll(bean.getSwaggerInfos());
            }
        }
        if (applicationContext instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext;
            for (SwaggerConfigBean swaggerInfo : swaggerInfos) {
                String apiMsg = String.format("%s:%s", swaggerInfo.getGroupName(), swaggerInfo.getApiBasePackage());
                SwaggerConfigPool.addSwaggerBean(apiMsg, swaggerInfo);
                registry.registerBeanDefinition(apiMsg,
                        new RootBeanDefinition(SwaggerFactoryBean.class));
            }
        }
    }

    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof SwaggerFactoryBean) {
            SwaggerFactoryBean tempBean = (SwaggerFactoryBean) bean;
            tempBean.setBeanName(beanName);
        }
        return bean;
    }
}
