package vip.breakpoint.listener;

import com.alibaba.fastjson2.JSONObject;
import vip.breakpoint.enums.ChangeTypeEnum;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.SpringChangeValueUtils;
import vip.breakpoint.wrapper.SpringBeanWrapper;
import vip.breakpoint.wrapper.SpringBeanWrapperPool;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * spring的配置
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
public class SpringContextConfigChangeListener implements ConfigChangeListener {

    private static final Logger log = WebLogFactory.getLogger(SpringContextConfigChangeListener.class);

    @Override
    public void doChangedConfigFileRefresh(ChangeTypeEnum changeTypeEnum, File file,
                                           Map<String, String> changeFileKeyValueMap) {
        if (ChangeTypeEnum.FILE == changeTypeEnum) {
            log.info("the file have changed!! the path is:{}", file.getAbsolutePath());
        } else {
            log.info("the remote config have changed!! the value is:{}", JSONObject.toJSONString(changeFileKeyValueMap));
        }
        for (Map.Entry<String, String> entry : changeFileKeyValueMap.entrySet()) {
            Set<SpringBeanWrapper> beanWrappers =
                    SpringBeanWrapperPool.getSpringBeanWrapperByKey(entry.getKey());
            SpringChangeValueUtils.updateTheBeanValues(entry.getValue(), beanWrappers, false);
        }
    }
}
