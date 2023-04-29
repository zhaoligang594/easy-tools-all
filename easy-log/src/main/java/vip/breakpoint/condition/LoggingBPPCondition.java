package vip.breakpoint.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import vip.breakpoint.process.LoggingBeanPostProcessor;

/**
 * @author : breakpoint/赵先生
 * create on 2023/2/18
 * 欢迎关注公众号:代码废柴
 */
public class LoggingBPPCondition implements Condition {

    @Override
    public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        if (null != beanFactory) {
            String[] beanNamesForType = beanFactory.getBeanNamesForType(LoggingBeanPostProcessor.class);
            if (beanNamesForType.length > 0) {
                return false;
            }
        }
        return true;
    }
}