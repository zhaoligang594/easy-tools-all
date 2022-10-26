package vip.breakpoint.filter;

import vip.breakpoint.enums.FileTypeEnum;

import java.io.File;
import java.io.FileFilter;
import java.util.Set;

/**
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
public class MonitorConfigFilter implements FileFilter {

    private final Set<FileTypeEnum> fileTypeEnumSet;

    public MonitorConfigFilter(Set<FileTypeEnum> fileTypeEnumSet) {
        this.fileTypeEnumSet = fileTypeEnumSet;
    }

    @Override
    public boolean accept(File pathname) {
        // 监听特殊的文件
        return isCandidateFileType(pathname, fileTypeEnumSet);
    }

    private boolean isCandidateFileType(File file, Set<FileTypeEnum> fileTypeEnumSet) {
        if (null != file && file.isFile()) {
            for (FileTypeEnum typeEnum : fileTypeEnumSet) {
                boolean ret = file.getName().endsWith(typeEnum.getFileType());
                if (ret) {
                    return true;
                }
            }
        }
        return false;
    }
}
