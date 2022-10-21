package vip.breakpoint.listener;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
public class ConfigFileListener extends FileAlterationListenerAdaptor {

    @Override
    public void onStart(final FileAlterationObserver observer) {
        // omit...
    }

    @Override
    public void onFileChange(File file) {
        System.out.println("文件已经更新");
    }

    @Override
    public void onStop(final FileAlterationObserver observer) {
        // omit...
    }
}
