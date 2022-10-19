package vip.breakpoint.enums;

/**
 * 文件类型
 *
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
public enum FileTypeEnum {
    /**
     * PROPERTIES
     */
    PROPERTIES(".properties", "key value 配置文件");
    private final String fileType;
    private final String desc;

    FileTypeEnum(String fileType, String desc) {
        this.fileType = fileType;
        this.desc = desc;
    }

    public String getFileType() {
        return fileType;
    }

    public String getDesc() {
        return desc;
    }
}
