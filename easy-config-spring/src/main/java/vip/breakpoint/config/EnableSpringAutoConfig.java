package vip.breakpoint.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import vip.breakpoint.condition.ThreadPoolCondition;
import vip.breakpoint.listener.ConfigChangeListener;
import vip.breakpoint.listener.ConfigChangeListenerPool;
import vip.breakpoint.listener.SpringContextConfigChangeListener;
import vip.breakpoint.listener.SpringContextStartedListener;
import vip.breakpoint.remote.ConfigChangeService;
import vip.breakpoint.remote.impl.ConfigChangeServiceImpl;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
@Configuration
public class EnableSpringAutoConfig {

    @Bean
    public ApplicationListener<ContextRefreshedEvent> getSpringContextStartedListener() {
        return new SpringContextStartedListener();
    }

    @Bean
    public ConfigChangeListener getSpringContextFileChangeListener() {
        return new SpringContextConfigChangeListener();
    }

    @Bean
    public EasyConfigSpringBeanPostProcessor getValueAndBeanPoolContext() {
        return new EasyConfigSpringBeanPostProcessor();
    }

    @Conditional({ThreadPoolCondition.class})
    @Bean
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor res = new ThreadPoolTaskExecutor();
        res.setCorePoolSize(5);
        res.setMaxPoolSize(10);
        res.setQueueCapacity(50);
        res.setKeepAliveSeconds(300);
        res.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return res;
    }

    @Bean
    public ConfigChangeListenerPool getConfigChangeListenerPool() {
        return new ConfigChangeListenerPool();
    }

    @Bean
    public ConfigChangeService getConfigChangeService(ThreadPoolTaskExecutor executor,
                                                      ConfigChangeListenerPool configChangeListenerPool) {
        return new ConfigChangeServiceImpl(executor, configChangeListenerPool);
    }
}
