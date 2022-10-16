### 一、项目解释：

#### 1.1 项目的主要功能

* 该项目是一个日志的中间件，实现可插拔的日志打印。
* 打印日志的范围主要是对于接口的方法进行打印。

#### 1.2 主要的使用范围

* 无侵入的打印业务逻辑层的执行日志
* 统一的实现，避免了其他实现的繁杂的配置以及实现的过程

### 二、简单开始组件

#### 2.1 引入依赖

最新版本：

```xml
<dependency>
  <groupId>vip.breakpoint</groupId>
  <artifactId>logging-web</artifactId>
  <version>1.1.0</version>
</dependency>
```

#### 2.2 首先在我们的配置类上开启日志功能

注解配置：

```java
@Configuration
@ComponentScan(basePackages = {"vip.breakpoint.bean", "vip.breakpoint.service"})
// 开启日志打印功能
@EnableLoggingConfiguration
@Import({MyAspectj.class})
public class MainConfig {
}
```

xml配置：

```xml
<!--  日志配置  -->
    <bean class="vip.breakpoint.XmlEnableLoggingConfiguration"/>
```

#### 2.3 在我们的接口上使用配置打印日志的方法

##### 2.3.1 接口上配置注解

```java
@WebLogging(methods = {"add"})
public interface MyService {
    int add(int a, int b);
}
```

##### 2.3.2 实现类上配置注解

```java
/**
 * @author :breakpoint/赵立刚
 * @date : 2020/07/16
 */
@Slf4j
@Service
@WebLogging(methods = {"sub"})
public class SubService {

    public int sub(int a, int b) {
        log.info("vip.breakpoint.service.SubService.sub");
        //int i = 1 / 0;
        return a - b;
    }
}
```

#### 2.4 自定义回调组件

> 继承 EasyLoggingHandleAdaptor 或者实现  EasyLoggingHandle

```java
/**
 * @author :breakpoint/赵立刚
 * @date : 2020/07/16
 */
@Service
public class MyEasyLoggingHandle extends EasyLoggingHandleAdaptor {

    /**
     * before invoke method process
     *
     * @param methodName is methodName
     * @param methodArgs is req args
     */
    @Override
    public void invokeBefore(String methodName, Object[] methodArgs) {
      // 自己的业务逻辑书写的地方，在方法之前调用
        System.out.println("vip.breakpoint.service.MyEasyLoggingHandle.invokeBefore");
    }

    /**
     * after invoke method process
     *
     * @param methodName is methodName
     * @param methodArgs is req args
     * @param resVal     is return value
     */
    @Override
    public void invokeAfter(String methodName, Object[] methodArgs, Object resVal) {
      // 自己的业务逻辑书写的地方，在方法之后调用
        System.out.println("vip.breakpoint.service.MyEasyLoggingHandle.invokeAfter");
    }

    @Override
    public void invokeOnThrowing(String methodName, Object[] methodArgs, Throwable e) throws Throwable {
      	// 自己的业务逻辑书写的地方，在出现异常调用
        super.invokeOnThrowing(methodName, methodArgs, e);
    }
}
```

#### 2.5 测试数据执行结果

```shell
# 执行方法前，logger 日志打印
06:33:00.135 [main] INFO vip.breakpoint.factory.LoggingCGlibMethodInterceptor - request params:【[12,3]】||request method:【sub】|| request time :【2020-07-17 06:33:00】
# 调用回调定义组件 回调 方法执行前
vip.breakpoint.service.MyEasyLoggingHandle.invokeBefore
## 调用 Spring自己的前置通知
com.breakpoint.vip.aspectj.MyAspectj.pointcut.before
## 调用 真正的方法
06:33:00.153 [main] INFO vip.breakpoint.service.SubService - vip.breakpoint.service.SubService.sub
## 调用 Spring自己的后置
com.breakpoint.vip.aspectj.MyAspectj.pointcut.after
## 调用 Spring自己的返回前通知
com.breakpoint.vip.aspectj.MyAspectj.afterReturning
## 执行方法后，logger 日志打印
06:33:00.153 [main] INFO vip.breakpoint.factory.LoggingCGlibMethodInterceptor - request params:【[12,3]】||request method:【sub】|| complete time:【2020-07-17 06:33:00】||return val:【9】
# 调用回调定义组件 回调 方法执行后
vip.breakpoint.service.MyEasyLoggingHandle.invokeAfter
```

### 三、项目依赖

* JDK 1.8
* Spring项目环境

### 四、项目结构

```shell
.
├── README.md
├── pic
│   └── image-20200715171512729.png
├── pom.xml
└── src
    └── main
        └── java
            └── vip
                └── breakpoint
                    ├── XmlEnableLoggingConfiguration.java
                    ├── annotion
                    │   ├── EnableLoggingConfiguration.java
                    │   └── WebLogging.java
                    ├── config
                    │   └── LoggingBeanDefinitionRegistrar.java
                    ├── definition
                    │   └── ObjectMethodDefinition.java
                    ├── exception
                    │   └── MultiInterfaceBeansException.java
                    ├── factory
                    │   ├── EasyLoggingHandle.java
                    │   ├── EasyLoggingHandleAdaptor.java
                    │   ├── LoggingCGlibMethodInterceptor.java
                    │   ├── LoggingFactory.java
                    │   ├── LoggingJDKMethodInterceptor.java
                    │   └── LoggingMethodInterceptorSupport.java
                    ├── log
                    │   ├── EventConstants.java
                    │   ├── LoggingLevel.java
                    │   ├── WebLogFactory.java
                    │   └── adaptor
                    │       ├── ConsoleLoggerImpl.java
                    │       ├── DelegateLoggerImpl.java
                    │       ├── Logger.java
                    │       └── LoggerSupport.java
                    └── process
                        └── LoggingBeanPostProcessor.java
```

### 五、写在最后

由于作者水平有限，欢迎各位issue，以及提新增需求

### 六、视频介绍

[日志中间件简介](https://www.bilibili.com/video/BV1ND4y1D7NL/)

[https://www.bilibili.com/video/BV1ND4y1D7NL/](https://www.bilibili.com/video/BV1ND4y1D7NL/)
