package vip.breakpoint.listener;

import vip.breakpoint.supplier.base.PropertiesContextPool;

import java.io.File;
import java.util.Map;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class PoolConfigFileChangeListener implements FileChangeListener {
    @Override
    public void doChangedConfigFileRefresh(File file, Map<String, String> changeFileKeyValueMap) {
        PropertiesContextPool.refreshConfigValue(changeFileKeyValueMap);
    }
}
