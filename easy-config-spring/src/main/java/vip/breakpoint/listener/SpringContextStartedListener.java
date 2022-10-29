package vip.breakpoint.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import vip.breakpoint.engine.ConfigFileMonitorEngine;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class SpringContextStartedListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = WebLogFactory.getLogger(SpringContextStartedListener.class);

    @Autowired
    private List<FileChangeListener> fileChangeListenerList;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String[] beanNamesForType =
                applicationContext.getBeanNamesForType(ThreadPoolTaskExecutor.class);
        ThreadPoolTaskExecutor executor = null;
        if (beanNamesForType.length > 0) {
            executor = applicationContext.getBean(beanNamesForType[0], ThreadPoolTaskExecutor.class);
        }
        log.info("the spring context environment started!! next we start the file monitor!");
        ConfigFileMonitorEngine.startFileMonitorEngine(new ArrayList<>(), fileChangeListenerList, executor);
        log.info("start the file monitor success!!");
    }
}
