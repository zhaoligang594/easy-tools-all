package vip.breakpoint.listener;

import java.io.File;
import java.util.Map;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public interface FileChangeListener {

    /**
     * 更新文件
     *
     * @param file                  变更的文件
     * @param changeFileKeyValueMap 改变文件的值
     */
    void doChangedConfigFileRefresh(File file, Map<String, String> changeFileKeyValueMap);
}