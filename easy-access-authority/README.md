## 简单而又强大的访问控制组件：easy-access-authority
### 一、简单的介绍
在简单、复杂的系统中，某一些对外提供的接口需要访问控制，并且还有限流的操作。
这时候，需要一定的访问控制组件限制用户的操作的行为。这个组件就是为了解决这个问题的。
目前该组件定义了一系列接口可以操作的方法，如下所示：
```java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 两次点击间隔时间  默认时间1000ms  单位ms
     *
     * @return 间隔时间
     */
    long interval() default 1000L;

    /**
     * 是否启用限流防刷的操作
     *
     * @return 是否启动限流
     */
    boolean enableClickLimit() default false;

    /**
     * 接口访问是否需要进行登录
     *
     * @return 访问接口是否需要登录
     */
    boolean isLogIn() default true;

    /**
     * 该接口是否可用
     * 设置成 false 不可以进行访问
     *
     * @return 接口是否可用
     */
    boolean enable() default true;

    /**
     * 验证码是否需要进行验证
     *
     * @return 验证码是否验证
     */
    boolean isVerifyCode() default false;
}
```

### 二、核心功能

1. 限流防刷
2. 接口是否需要登录
3. 接口是否可用
4. 接口是否校验验证码

### 三、组件特性

#### 版本：1.0.0.RELEASE

```xml
<!-- https://mvnrepository.com/artifact/vip.breakpoint/easy-access-authority -->
<dependency>
    <groupId>vip.breakpoint</groupId>
    <artifactId>easy-access-authority</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```
第一个版本：接口的限流防刷、是否需要登录、接口是否可以访问、是否校验验证码。