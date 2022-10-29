package vip.breakpoint.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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
        log.info("the spring context environment started!! next we start the file monitor");
        ConfigFileMonitorEngine.startFileMonitorEngine(new ArrayList<>(), fileChangeListenerList);
    }
}
