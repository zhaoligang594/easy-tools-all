package vip.breakpoint.monitor;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import vip.breakpoint.filter.MonitorConfigFilter;

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

    private final FileAlterationMonitor monitor;

    public ConfigFileMonitor(FileAlterationMonitor monitor) {
        this.monitor = monitor;
    }

    /**
     * 给文件添加监听
     *
     * @param file     文件
     * @param listener 文件监听器
     */
    public void monitor(File filePath, FileFilter fileFilter, FileAlterationListener listener) {
        FileAlterationObserver observer = new FileAlterationObserver(filePath, fileFilter);
        monitor.addObserver(observer);
        observer.addListener(listener);
    }

    public void stop() throws Exception {
        System.out.println("stop the monitor");
        monitor.stop();
    }

    public void start() throws Exception {
        System.out.println("start the monitor");
        monitor.start();
    }

}
