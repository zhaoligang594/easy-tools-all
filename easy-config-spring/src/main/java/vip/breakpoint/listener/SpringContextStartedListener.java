package vip.breakpoint.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import vip.breakpoint.bean.StreamVo;
import vip.breakpoint.config.AnnConfig;
import vip.breakpoint.engine.ConfigFileMonitorEngine;
import vip.breakpoint.enums.FileTypeEnum;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.supplier.base.PropertiesContextPool;
import vip.breakpoint.utils.EasyColUtils;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.SpringChangeValueUtils;
import vip.breakpoint.wrapper.SpringBeanWrapper;
import vip.breakpoint.wrapper.SpringBeanWrapperPool;

import java.io.IOException;
import java.util.*;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class SpringContextStartedListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = WebLogFactory.getLogger(SpringContextStartedListener.class);

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    @Autowired
    private ConfigChangeListenerPool configChangeListenerPool;

    private FileTypeEnum getFileType(String classpathFile) {
        if (EasyStringUtils.isBlank(classpathFile)) return null;
        if (classpathFile.contains(FileTypeEnum.PROPERTIES.getFileType())) {
            return FileTypeEnum.PROPERTIES;
        }
        if (classpathFile.contains(FileTypeEnum.YAML.getFileType())) {
            return FileTypeEnum.YAML;
        }
        if (classpathFile.contains(FileTypeEnum.JSON.getFileType())) {
            return FileTypeEnum.JSON;
        }
        return null;
    }

    private List<StreamVo> getAllClasspathResources() {
        List<StreamVo> retList = new ArrayList<>();
        // 获取到必须要读取的文件
        List<String> classpathFiles = AnnConfig.classpathFiles;
        if (EasyColUtils.isNotEmpty(classpathFiles)) {
            Set<String> haveExecuted = new HashSet<>();
            for (String classpathFile : classpathFiles) {
                if (!haveExecuted.add(classpathFile)) {
                    continue;
                }
                if (EasyStringUtils.isNotBlank(classpathFile)) {
                    FileTypeEnum fileType = getFileType(classpathFile);
                    if (null == fileType) continue;
                    try {
                        Resource[] resources = resourceResolver.getResources(classpathFile);
                        for (Resource resource : resources) {
                            retList.add(new StreamVo(resource.getFilename(), fileType, resource.getInputStream()));
                        }
                    } catch (IOException e) {
                        log.warn("读取异常", e);
                    }
                }
            }
        }
        return retList;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 初始化必要的数据
        PropertiesContextPool.initProperties(getAllClasspathResources());
        // 初始化 监听
        ApplicationContext applicationContext = event.getApplicationContext();
        String[] beanNamesForType =
                applicationContext.getBeanNamesForType(ThreadPoolTaskExecutor.class);
        ThreadPoolTaskExecutor executor = null;
        if (beanNamesForType.length > 0) {
            executor = applicationContext.getBean(beanNamesForType[0], ThreadPoolTaskExecutor.class);
        }
        log.info("the spring context environment started!! next we start the file monitor!");
        ConfigFileMonitorEngine.startFileMonitorEngine(new ArrayList<>(AnnConfig.fileSystemPaths),
                configChangeListenerPool.getConfigChangeListeners(), executor);
        log.info("start the file monitor success!!");
        // back up bean init
        initBackupBean();
    }

    private void initBackupBean() {
        Map<String, Set<SpringBeanWrapper>> backUpBeanMap = SpringBeanWrapperPool.getBackUpBeanMap();
        Map<String, String> configValuesMap = PropertiesContextPool.getConfigValuesMap();
        for (Map.Entry<String, Set<SpringBeanWrapper>> entry : backUpBeanMap.entrySet()) {
            String value = configValuesMap.get(entry.getKey());
            SpringChangeValueUtils.updateTheBeanValues(value, entry.getValue(), true);
            SpringBeanWrapperPool.addSpringBeanWrapper(entry.getKey(), entry.getValue());
        }
    }
}
