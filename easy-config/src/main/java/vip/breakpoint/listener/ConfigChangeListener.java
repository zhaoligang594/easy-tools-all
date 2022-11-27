package vip.breakpoint.listener;

import vip.breakpoint.enums.ChangeTypeEnum;

import java.io.File;
import java.util.EventListener;
import java.util.Map;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public interface ConfigChangeListener extends EventListener {

    /**
     * 更新文件
     *
     * @param file                  变更的文件
     * @param changeFileKeyValueMap 改变文件的值
     * @param changeTypeEnum        修改值的类型
     */
    void doChangedConfigFileRefresh(ChangeTypeEnum changeTypeEnum, File file,
                                    Map<String, String> changeFileKeyValueMap);
}
