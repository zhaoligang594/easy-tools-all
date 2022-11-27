package vip.breakpoint.swagger.bean;

/**
 * @author : breakpoint/赵先生
 * create on 2022/11/05
 * 欢迎关注公众号:代码废柴
 */
public class SwaggerConfigBean {
    // 扫描的接口的地址
    private String apiBasePackage;
    // 分组名字
    private String groupName;
    // 这个分组的接口信息
    private SwaggerApiInfoBean apiInfoBean;

    public SwaggerConfigBean(String apiBasePackage, String name) {
        this.apiBasePackage = apiBasePackage;
        this.groupName = name;
    }

    public SwaggerConfigBean() {
    }

    public String getApiBasePackage() {
        return apiBasePackage;
    }

    public void setApiBasePackage(String apiBasePackage) {
        this.apiBasePackage = apiBasePackage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public SwaggerApiInfoBean getApiInfoBean() {
        return apiInfoBean;
    }

    public void setApiInfoBean(SwaggerApiInfoBean apiInfoBean) {
        this.apiInfoBean = apiInfoBean;
    }
}
