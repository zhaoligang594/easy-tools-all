package vip.breakpoint.listener;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import vip.breakpoint.executor.FileChangeListener;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
public class ConfigFileListener extends FileAlterationListenerAdaptor {

    private final List<FileChangeListener> fileChangeListeners = new ArrayList<>();

    private Executor executor;

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

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
        if (null != executor) {
            for (FileChangeListener fileChangeListener : fileChangeListeners) {
                executor.execute(() -> fileChangeListener.doChangedConfigFileRefresh(file));
            }
        } else {
            for (FileChangeListener fileChangeListener : fileChangeListeners) {
                fileChangeListener.doChangedConfigFileRefresh(file);
            }
        }
        log.info("the file have refresh done:" + file.getAbsolutePath());
    }

    @Override
    public void onStop(final FileAlterationObserver observer) {
        // omit...
    }

    // 添加文件的监听器
    public void addFileChangeListener(Collection<FileChangeListener> listeners) {
        if (null != listeners && listeners.size() > 0) {
            fileChangeListeners.addAll(listeners);
        }
        // default the fileChangeListener
        fileChangeListeners.add(new PoolConfigFileChangeListener());
    }
}
