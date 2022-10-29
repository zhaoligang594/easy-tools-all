package vip.breakpoint.listener;

import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.io.File;
import java.util.Map;

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
    }
}
