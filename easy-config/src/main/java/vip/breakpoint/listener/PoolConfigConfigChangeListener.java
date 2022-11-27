package vip.breakpoint.listener;

import vip.breakpoint.enums.ChangeTypeEnum;
import vip.breakpoint.supplier.base.PropertiesContextPool;

import java.io.File;
import java.util.Map;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class PoolConfigConfigChangeListener implements ConfigChangeListener {
    @Override
    public void doChangedConfigFileRefresh(ChangeTypeEnum changeTypeEnum,
                                           File file, Map<String, String> changeFileKeyValueMap) {
        PropertiesContextPool.refreshConfigValue(changeFileKeyValueMap);
    }
}
