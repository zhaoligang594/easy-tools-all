package vip.breakpoint.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/27
 * 欢迎关注公众号:代码废柴
 */
public class ConfigChangeListenerPool implements ApplicationContextAware {

    private final List<ConfigChangeListener> configChangeListeners=new CopyOnWriteArrayList<>();

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(ConfigChangeListener.class);
        for (String beanName : beanNamesForType) {
            configChangeListeners.add(applicationContext.getBean(beanName, ConfigChangeListener.class));
        }
    }

    public List<ConfigChangeListener> getConfigChangeListeners() {
        return configChangeListeners;
    }
}
