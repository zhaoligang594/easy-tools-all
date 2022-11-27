package vip.breakpoint.swagger.bean;

import org.springframework.beans.factory.FactoryBean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import vip.breakpoint.swagger.config.SwaggerConfigPool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : breakpoint/赵先生
 * create on 2022/11/05
 * 欢迎关注公众号:代码废柴
 */
public class SwaggerFactoryBean implements FactoryBean<Docket> {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String beanName;

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public SwaggerFactoryBean() {
    }

    @Override
    public Docket getObject() throws Exception {
        SwaggerConfigBean bean = SwaggerConfigPool.getBean(beanName);
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(bean.getApiBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                //.directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
                //.directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo(bean.getApiInfoBean())).groupName(bean.getGroupName());
    }

    @Override
    public Class<?> getObjectType() {
        return Docket.class;
    }

    public ApiInfo apiInfo(SwaggerApiInfoBean apiInfoBean) {
        return new ApiInfoBuilder()
                .title(apiInfoBean.getTitle())
                .description(apiInfoBean.getDescription())
                .license(apiInfoBean.getLicense())
                .licenseUrl(apiInfoBean.getLicenseUrl())
                .termsOfServiceUrl(apiInfoBean.getTermsOfServiceUrl())
                .version(String.format("%s last update time:%s", apiInfoBean.getVersion(), sdf.format(new Date())))
                .contact(apiInfoBean.getContact())
                .build();
    }
}
