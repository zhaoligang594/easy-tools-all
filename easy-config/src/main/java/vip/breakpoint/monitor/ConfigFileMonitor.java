package vip.breakpoint.monitor;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import vip.breakpoint.config.ConfigFileMonitorConfig;
import vip.breakpoint.filter.MonitorConfigFilter;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.io.File;
import java.io.FileFilter;

/**
 * 文件监听器
 *
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
public class ConfigFileMonitor {
    /**
     * 日志的操作
     */
    private static final Logger log = WebLogFactory.getLogger(ConfigFileMonitor.class);

    private final FileAlterationMonitor monitor;

    public ConfigFileMonitor(FileAlterationMonitor monitor) {
        this.monitor = monitor;
    }

    /**
     * 给文件添加监听
     *
     * @param filePath   文件路径
     * @param listener   文件监听器
     * @param fileFilter 文件过滤器
     */
    public void monitor(File filePath, FileFilter fileFilter, FileAlterationListener listener) {
        FileAlterationObserver observer = new FileAlterationObserver(filePath, fileFilter);
        monitor.addObserver(observer);
        observer.addListener(listener);
    }

    public void stop() throws Exception {
        log.info("stop the monitor");
        monitor.stop();
    }

    public void start() throws Exception {
        log.info("start the monitor");
        monitor.start();
    }

}
