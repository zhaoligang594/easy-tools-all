package vip.breakpoint.swagger.config;

import org.springframework.lang.NonNull;
import springfox.documentation.service.Contact;
import vip.breakpoint.swagger.bean.SwaggerApiInfoBean;
import vip.breakpoint.swagger.bean.SwaggerConfigBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : breakpoint/赵先生
 * create on 2022/11/05
 * 欢迎关注公众号:代码废柴
 */
public class SwaggerConfigPool {

    private static final Map<String, SwaggerConfigBean> key2SwaggerMap = new HashMap<>();

    private static final SwaggerConfigBean commonBean = new SwaggerConfigBean();

    static {
        commonBean.setGroupName("欢迎关注《代码废柴》公众号");
        commonBean.setApiBasePackage("");
        commonBean.setApiInfoBean(new SwaggerApiInfoBean("项目接口文档", "项目接口文档描述", "服务连接",
                new Contact("赵先生", "http://www.breakpoint.vip", "zlgtop@163.com"),
                "license", "license url", "版本"));
    }

    public static void addSwaggerBean(String key, SwaggerConfigBean bean) {
        if (null == bean.getApiInfoBean()) {
            bean.setApiInfoBean(commonBean.getApiInfoBean());
        }
        key2SwaggerMap.put(key, bean);
    }

    @NonNull
    public static SwaggerConfigBean getBean(String key) {
        return key2SwaggerMap.getOrDefault(key, commonBean);
    }
}
