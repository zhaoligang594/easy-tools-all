package vip.breakpoint.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;
import vip.breakpoint.service.UserStoreService;

/**
 * @author : breakpoint
 * create on 2022/10/16
 * 欢迎关注公众号 《代码废柴》
 */
public class UserStoreCondition implements Condition {

    @Override
    public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        if (null != beanFactory) {
            String[] beanNamesForType = beanFactory.getBeanNamesForType(UserStoreService.class);
            if (beanNamesForType.length > 0) {
                return false;
            }
        }
        return true;
    }
}
