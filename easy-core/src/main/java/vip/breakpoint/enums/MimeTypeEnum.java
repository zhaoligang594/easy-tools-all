package vip.breakpoint.enums;

/**
 * MimeTypeEnum constants
 *
 * @author :breakpoint
 * create on 2017/11/03
 */
public enum MimeTypeEnum {
    // constants
    APPLICATION_ATOM_XML("application/atom+xml"),
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
    APPLICATION_JSON("application/json"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    APPLICATION_SVG_XML("application/svg+xml"),
    APPLICATION_XHTML_XML("application/xhtml+xml"),
    APPLICATION_XML("application/xml"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml"),
    WILDCARD("*/*");

    private final String mimeType;

    MimeTypeEnum(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    /**
     * 获取默认的MimeType
     *
     * @return MimeTypeEnum
     */
    public static MimeTypeEnum getDefaultMimeType() {
        return TEXT_PLAIN;
    }

}
