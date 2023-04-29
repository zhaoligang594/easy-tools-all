package vip.breakpoint.annotation;

import org.springframework.context.annotation.Import;
import vip.breakpoint.annontation.EnableAutoConfig;
import vip.breakpoint.config.EnableAccessBeanDefinitionRegistrar;
import vip.breakpoint.config.EnableAccessAnnBeanDefinitionRegistrar;
import vip.breakpoint.config.WebConfigAdapter;

import java.lang.annotation.*;

/**
 * 开启拦截功能
 *
 * @author : breakpoint
 * create on 2022/10/16
 * 欢迎关注公众号 《代码废柴》
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAutoConfig
@Import({EnableAccessBeanDefinitionRegistrar.class,
        EnableAccessAnnBeanDefinitionRegistrar.class,
        WebConfigAdapter.class})
public @interface EnableAccessLimit {

    /**
     * enable the swagger api
     *
     * @return true or  false
     */
    boolean enableSwagger() default true;


    /**
     * 接口开启RBAC功能
     *
     * @return 开启或者关闭
     */
    boolean enableRBAC() default false;

    /**
     * 开启请求参数的 日期的解析
     *
     * @return 返回是否解析日期
     */
    boolean enableDateParamParser() default true;

    /**
     * 文件系统的监听路径 与 @EnableAutoConfig 中的
     *
     * @return 文件系统的监听
     * @see vip.breakpoint.annontation.EnableAutoConfig fileSystemPaths 作用一致
     */
    String[] configFileSystemPaths() default {};

    /**
     * 可以不需要注解进行请求的路径
     *
     * @return 可以忽略的路径集合
     */
    String[] ignorePaths() default {};
}
