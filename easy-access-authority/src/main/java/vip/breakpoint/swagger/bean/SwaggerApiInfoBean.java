package vip.breakpoint.swagger.bean;

import springfox.documentation.service.Contact;

/**
 * @author : breakpoint/赵先生
 * create on 2022/11/05
 * 欢迎关注公众号:代码废柴
 */
public class SwaggerApiInfoBean {
    // 标题
    private String title;
    // 描述
    private String description;
    // 服务地址
    private String termsOfServiceUrl;
    // 联系方式
    private Contact contact;
    // license
    private String license;
    // licenseUrl
    private String licenseUrl;
    // version
    private String version;

    public SwaggerApiInfoBean(String title, String description,
                              String termsOfServiceUrl, Contact contact, String
                                      license, String licenseUrl, String version) {
        this.title = title;
        this.description = description;
        this.termsOfServiceUrl = termsOfServiceUrl;
        this.contact = contact;
        this.license = license;
        this.licenseUrl = licenseUrl;
        this.version = version;

    }

    public SwaggerApiInfoBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
