package vip.breakpoint.bean;

import vip.breakpoint.enums.FileTypeEnum;

import java.io.InputStream;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/25
 * 欢迎关注公众号:代码废柴
 */
public class StreamVo {

    private String fileName;

    private FileTypeEnum typeEnum;

    private InputStream inputStream;

    public StreamVo(String fileName, FileTypeEnum typeEnum, InputStream inputStream) {
        this.fileName = fileName;
        this.typeEnum = typeEnum;
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(FileTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
