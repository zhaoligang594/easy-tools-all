package vip.breakpoint.config;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 注解的配置
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/25
 * 欢迎关注公众号:代码废柴
 */
public class AnnConfig {

    // from the EnableAutoConfig
    public static final String FILE_SYSTEM_PATHS_KEY = "fileSystemPaths";
    public static final String CLASSPATH_FILES_KEY = "classpathFiles";

    public static final Set<String> fileSystemPaths = new HashSet<>();
    public static final Set<String> ignorePaths = new HashSet<>();
    public static final List<String> classpathFiles = new CopyOnWriteArrayList<>();

    static {
        // default 需要加载的文件 如果需要配置其他 需要自己另外配置
        classpathFiles.addAll(new ArrayList<>(Arrays.asList("classpath*:*.properties",
                "classpath*:*.yml", "classpath*:*.json")));
    }
}
