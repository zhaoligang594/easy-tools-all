package vip.breakpoint.engine;

import vip.breakpoint.config.ConfigFileMonitorConfig;
import vip.breakpoint.enums.FileTypeEnum;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    private static final String CLASS_PATH_PREFIX = "classpath:";


    public List<String> getFilePath(String originFilePath) {
        String basePath = ConfigFileMonitorEngine.class.getResource("/").getPath();
        String parent = new File(basePath).getParent();
        monitorConfig.addMonitorFile(new String[]{parent}, FileTypeEnum.PROPERTIES);
        if (originFilePath.startsWith(CLASS_PATH_PREFIX)) {
            URL resource = ConfigFileMonitorEngine.class.getResource(originFilePath);
        }
        return new ArrayList<>();
    }

}
