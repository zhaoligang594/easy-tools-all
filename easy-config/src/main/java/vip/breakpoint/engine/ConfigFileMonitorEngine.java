package vip.breakpoint.engine;

import vip.breakpoint.config.ConfigFileMonitorConfig;
import vip.breakpoint.enums.FileTypeEnum;

import java.io.File;
import java.net.URL;
import java.util.*;

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
    public void setMonitorDefaultClassPath() {
        String basePath = ConfigFileMonitorEngine.class.getResource("/").getPath();
        if (basePath.contains("target")) {
            basePath = basePath.substring(0, basePath.indexOf("target"));
        }
        monitorFilePathSet.add(basePath);
    }


    public List<String> getFileSystemPath() {
        return new ArrayList<>();
    }

    /**
     * 设置监听文件的类型
     */
    public void setMonitorFileTypes(Set<FileTypeEnum> monitorFileTypeSet) {
        if (null == monitorFileTypeSet) return;
        this.monitorFileTypeSet.addAll(monitorFileTypeSet);
    }

    public void setMonitorFileTypes(FileTypeEnum... monitorFileTypes) {
        if (null == monitorFileTypes) return;
        this.monitorFileTypeSet.addAll(Arrays.asList(monitorFileTypes));
    }

    /**
     * 启动监听引擎
     * 监听指定的文件的更改路径以及文件的文件的类型
     */
    public void startMonitorEngine() {
        monitorConfig.addMonitorFile(new ArrayList<>(monitorFilePathSet), monitorFileTypeSet);
    }
}
