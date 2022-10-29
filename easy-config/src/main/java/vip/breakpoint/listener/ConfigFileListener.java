package vip.breakpoint.listener;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.FetchKeyValueUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    public void onFileChange(final File file) {
        log.info("config have some change:" + file.getAbsolutePath());
        final Map<String, String> key2ValueMap = FetchKeyValueUtils.getKey2ValueMap(file);
        if (null != executor) {
            for (FileChangeListener fileChangeListener : fileChangeListeners) {
                executor.execute(() -> fileChangeListener.doChangedConfigFileRefresh(file, key2ValueMap));
            }
        } else {
            for (FileChangeListener fileChangeListener : fileChangeListeners) {
                fileChangeListener.doChangedConfigFileRefresh(file, key2ValueMap);
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
