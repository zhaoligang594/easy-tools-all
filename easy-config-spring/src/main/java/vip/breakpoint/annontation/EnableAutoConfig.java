package vip.breakpoint.annontation;

import org.springframework.context.annotation.Import;
import vip.breakpoint.config.EnableAutoConfigBeanDefinitionRegistrar;

import java.lang.annotation.*;

/**
 * 启动自动更新的配置
 *
 * @author : breakpoint/赵先生
 * create on 2022/10/28
 * 欢迎关注公众号:代码废柴
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EnableAutoConfigBeanDefinitionRegistrar.class})
public @interface EnableAutoConfig {

    /**
     * 文件系统的监听路径
     *
     * @return 文件系统的监听
     */
    String[] fileSystemPaths() default {};

    /**
     * classpath 监听的文件
     * 用于启动后的配置
     * 打包后 jar 或者 war包里面的配置 有可能读取不到
     * 这个就是解决这个问题的 防止出现取不到值的问题
     */
    String[] classpathFiles() default {"classpath*:*.properties", "classpath*:*.yml", "classpath*:*.json"};
}
