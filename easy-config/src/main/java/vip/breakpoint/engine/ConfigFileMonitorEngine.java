package vip.breakpoint.engine;

import vip.breakpoint.config.ConfigFileMonitorConfig;
import vip.breakpoint.enums.FileTypeEnum;
import vip.breakpoint.listener.ConfigChangeListener;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.EasyStringUtils;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * 文件监听执行引擎
 * 用户获取用户配置的文件地址
 * 以及启动文件的监听
 *
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
public class ConfigFileMonitorEngine {

    private ConfigFileMonitorEngine() {/* refuse the new object */}

    private static final Logger log = WebLogFactory.getLogger(ConfigFileMonitorEngine.class);

    private final ConfigFileMonitorConfig monitorConfig =
            new ConfigFileMonitorConfig(ConfigFileMonitorConfig.DEFAULT_INTERVAL);

    private final Set<String> monitorFilePathSet = new HashSet<>();

    private final Set<FileTypeEnum> monitorFileTypeSet = new HashSet<>();

    /**
     * 针对的是本地的启动测试用的一般情况下
     * 主要的用途是 找到自己的配置的文件 然后 进行监听自己的配置的文件
     */
    private static final String CLASS_PATH_PREFIX = "classpath:";

    private static final String CLASS_PATH_PREFIX_STAR = "classpath*:";

    /**
     * 默认设置监听项目里面所有符合配置的文件
     */
    private void setMonitorDefaultClassPath() {
        String basePath = ConfigFileMonitorEngine.class.getResource("/").getPath();
        if (basePath.contains("target")) {
            basePath = basePath.substring(0, basePath.indexOf("target"));
        }
        log.info("default monitor base path is:{}", basePath);
        monitorFilePathSet.add(basePath);
    }

    /**
     * 添加监控路径
     *
     * @param path 监控文件的路径
     */
    private void addMonitorFilePath(String path) {
        if (EasyStringUtils.isNotBlank(path)) {
            monitorFilePathSet.add(path);
        }
    }

    // 设置监听文件的类型
    private void setMonitorFileTypes(Set<FileTypeEnum> monitorFileTypeSet) {
        if (null == monitorFileTypeSet) return;
        this.monitorFileTypeSet.addAll(monitorFileTypeSet);
    }

    // 设置监听文件的类型
    private void setMonitorFileTypes(FileTypeEnum... monitorFileTypes) {
        if (null == monitorFileTypes) return;
        this.monitorFileTypeSet.addAll(Arrays.asList(monitorFileTypes));
    }

    /**
     * 启动监听引擎
     * 监听指定的文件的更改路径以及文件的文件的类型
     */
    private void startMonitorEngine() {
        monitorConfig.addMonitorFile(new ArrayList<>(monitorFilePathSet), monitorFileTypeSet);
    }

    /**
     * start the file monitor
     */
    public static void startFileMonitorEngine(List<ConfigChangeListener> configChangeListeners) {
        startFileMonitorEngine(new ArrayList<>(), configChangeListeners, null);
    }

    public void setFileChangeListeners(List<ConfigChangeListener> configChangeListeners) {
        if (null != configChangeListeners) {
            monitorConfig.setFileChangeListeners(configChangeListeners);
        }
    }

    public void setFileChangeExecutor(Executor executor) {
        monitorConfig.setExecutor(executor);
    }

    /**
     * start the file monitor
     */
    public static void startFileMonitorEngine(List<String> monitorPaths,
                                              List<ConfigChangeListener> configChangeListeners, Executor executor) {
        // create the ConfigFileMonitorEngine
        ConfigFileMonitorEngine engine = new ConfigFileMonitorEngine();
        // to set the default path
        engine.setMonitorDefaultClassPath();
        // add file to the engine
        monitorPaths.forEach(engine::addMonitorFilePath);
        // monitor file type
        engine.setMonitorFileTypes(FileTypeEnum.PROPERTIES, FileTypeEnum.JSON, FileTypeEnum.YAML);
        // start the file monitor engine
        engine.setFileChangeListeners(configChangeListeners);
        engine.setFileChangeExecutor(executor);
        engine.startMonitorEngine();
    }
}
