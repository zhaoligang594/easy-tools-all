package vip.breakpoint.listener;

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
public class SpringContextFileChangeListener implements FileChangeListener {

    private static final Logger log = WebLogFactory.getLogger(SpringContextFileChangeListener.class);

    @Override
    public void doChangedConfigFileRefresh(File file, Map<String, String> changeFileKeyValueMap) {
        log.info("the file have changed!! the path is:{}", file.getAbsolutePath());
        for (Map.Entry<String, String> entry : changeFileKeyValueMap.entrySet()) {
            Set<SpringBeanWrapper> beanWrappers =
                    SpringBeanWrapperPool.getSpringBeanWrapperByKey(entry.getKey());
            SpringChangeValueUtils.updateTheBeanValues(entry.getValue(), beanWrappers);
        }
    }
}
