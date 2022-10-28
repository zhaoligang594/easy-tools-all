package vip.breakpoint.listener;

import vip.breakpoint.executor.FileChangeListener;
import vip.breakpoint.supplier.base.PropertiesContextPool;

import java.io.File;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class PoolConfigFileChangeListener implements FileChangeListener {
    @Override
    public void doChangedConfigFileRefresh(File file) {
        PropertiesContextPool.refreshConfigFile(file);
    }
}
