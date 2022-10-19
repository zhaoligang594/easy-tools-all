package vip.breakpoint.filter;

import vip.breakpoint.enums.FileTypeEnum;

import java.io.File;
import java.io.FileFilter;

/**
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
public class MonitorConfigFilter implements FileFilter {

    private final FileTypeEnum fileType;

    public MonitorConfigFilter(FileTypeEnum fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean accept(File pathname) {
        System.out.println(pathname.getName().endsWith(fileType.getFileType()));
        return pathname.getName().endsWith(fileType.getFileType());
    }

}
