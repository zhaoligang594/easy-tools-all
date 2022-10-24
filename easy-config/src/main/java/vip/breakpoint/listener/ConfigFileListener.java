package vip.breakpoint.listener;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.supplier.base.ContextProperties;

import java.io.File;

/**
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
public class ConfigFileListener extends FileAlterationListenerAdaptor {

    /**
     * 日志的操作
     */
    private static final Logger log = WebLogFactory.getLogger(ConfigFileListener.class);

    @Override
    public void onStart(final FileAlterationObserver observer) {
        // omit...
    }

    @Override
    public void onFileChange(File file) {
        log.info("config have some change:" + file.getAbsolutePath());
        ContextProperties.refreshConfigFile(file);
        log.info("the file have refresh done:" + file.getAbsolutePath());
    }

    @Override
    public void onStop(final FileAlterationObserver observer) {
        // omit...
    }
}
