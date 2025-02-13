## 一、简单项目日志组件：easy-log组件

### 1.1 背景

> 在项目中，需要打印的日志地方有很多，如果实现打印日志，还需要自己实现AOP来实现方法的日志。每一次都实现，非常的不方便。因此，这个组件解决的是快速的实现方法级别的日志操作。可以快速的记录方法的操作日志，方便用户更加的专注业务代码。

### 1.2 核心功能

* 无侵入的打印业务逻辑层的执行日志

## 二、使用方法

### 2.1 引入最新的组件坐标到pom.xml中

```xml
<dependency>
    <groupId>vip.breakpoint</groupId>
    <artifactId>easy-log</artifactId>
    <version>XXX</version>
</dependency>
```

?> 这里强力推荐使用最新的组件包。

### 2.2 启动类配置

```java
@EnableEasyLogging
@EnableAccessLimit(ignorePaths = {"/sys", "/user"}, configFileSystemPaths = {"C:\\work\\idea_work"})
@EnableAutoConfig(fileSystemPaths = {"C:\\work\\idea_work"})
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

?> 上面的代码中，注解 `@EnableEasyLogging` 表明这个应用开启了日志的功能。

### 2.3 方法级别配置

在需要打印日志的位置，加上如下的注解：

```java
@Lazy
@Service
public class TestServiceImpl extends BaseService implements TestService {

    @EasyConfig(value = "test001:[{\"age\":-1,\"name\":\"default name\"}]")
    private List<TestUser> list2;

    @EasyLog
    @Override
    public Object test() {
        return list2;
    }

    @Override
    public Object test2() {
        return list;
    }
}
```

`@EasyLog` 注解定义了这个方法需要打印日志。下面就是一次输出的例子：

```shell
2022-11-29 20:02:14.831  INFO 22788 --- [io-8080-exec-10] v.b.h.LoggingCGlibMethodInterceptor      : request params:[[]],request method:[test], request time :[2022-11-29 20:02:14]
2022-11-29 20:02:14.831  INFO 22788 --- [io-8080-exec-10] v.b.h.LoggingCGlibMethodInterceptor      : response params:[[]],request method:[test], complete time:[2022-11-29 20:02:14],return val:[[{"age":222,"name":"name is 程序员LEO1231222"}]]
```

## 三、自定义日志handle

定义一个日志组件bean，并且实现下面的接口：

```java
/**
 * 日志回调
 *
 * @author :breakpoint/赵立刚
 */
public interface EasyLoggingHandle {

    /**
     * before invoke method process
     *
     * @param methodName  is methodName
     * @param methodArgs  is req args
     * @param annotations is annotations
     */
    void invokeBefore(String methodName, Object[] methodArgs, Annotation[] annotations);

    /**
     * after invoke method process
     *
     * @param methodName  is methodName
     * @param methodArgs  is req args
     * @param resVal      is return value
     * @param annotations is annotations
     */
    void invokeAfter(String methodName, Object[] methodArgs, Object resVal, Annotation[] annotations);

    /**
     * when throw exception
     *
     * @param methodName  is methodName
     * @param methodArgs  is req args
     * @param e           is throws exception
     * @param annotations is annotations
     * @throws Throwable
     */
    void invokeOnThrowing(String methodName, Object[] methodArgs, Annotation[] annotations, Throwable e)
            throws Throwable;

}
```

或者可以继承日志的适配器：

```java
/**
 * @author :breakpoint/赵立刚
 */
public abstract class EasyLoggingHandleAdaptor implements EasyLoggingHandle {

    @Override
    public void invokeBefore(String methodName,
                             Object[] methodArgs, Annotation[] annotations) {
        // for subclass implements
    }

    @Override
    public void invokeAfter(String methodName, Object[] methodArgs,
                            Object resVal, Annotation[] annotations) {
        // for subclass implements
    }

    @Override
    public void invokeOnThrowing(String methodName, Object[] methodArgs,
                                 Annotation[] annotations, Throwable e) throws Throwable {
        // for subclass implements
    }
}
```

!> 下面是一个实现的例子：

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.breakpoint.loghandle.EasyLoggingHandleAdaptor;

import java.lang.annotation.Annotation;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/02
 * 欢迎关注公众号:代码废柴
 */
@Slf4j
@Service
public class LogHandle extends EasyLoggingHandleAdaptor {

    @Override
    public void invokeBefore(String methodName, Object[] methodArgs, Annotation[] annotations) {
        log.info("invokeBefore");
    }

    @Override
    public void invokeAfter(String methodName, Object[] methodArgs, Object resVal, Annotation[] annotations) {
        log.info("invokeAfter");
    }

    @Override
    public void invokeOnThrowing(String methodName, Object[] methodArgs, Annotation[] annotations, Throwable e) throws Throwable {
        log.info("invokeOnThrowing");
    }
}
```

输出的日志：

```shell
2022-11-29 20:37:17.244  INFO 17176 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2022-11-29 20:37:17.244  INFO 17176 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-11-29 20:37:17.245  INFO 17176 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 0 ms
2022-11-29 20:37:17.261  INFO 17176 --- [nio-8080-exec-1] v.b.h.LoggingCGlibMethodInterceptor      : request params:[[]],request method:[test], request time :[2022-11-29 20:37:17]
2022-11-29 20:37:17.262  INFO 17176 --- [pool-1-thread-1] com.zlg.test.demo.log.LogHandle          : invokeBefore
2022-11-29 20:37:17.270  INFO 17176 --- [nio-8080-exec-1] v.b.h.LoggingCGlibMethodInterceptor      : response params:[[]],request method:[test], complete time:[2022-11-29 20:37:17],return val:[[{"age":25,"name":"name is 程序员冈刀"}]]
2022-11-29 20:37:17.271  INFO 17176 --- [pool-1-thread-1] com.zlg.test.demo.log.LogHandle          : invokeAfter
```

## 四、其他的配置

```java
/**
 * @author :breakpoint/赵立刚
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyLog {

    /**
     * 名字 默认选取方法的名字
     *
     * @return name
     */
    String name() default ""; //

    /**
     * 打印日志的时间格式
     *
     * @return pattern
     */
    String timePattern() default "yyyy-MM-dd HH:mm:ss"; // 日期的格式

    /**
     * 是否在系统中打印日志
     *
     * @return true or false
     */
    boolean logInApp() default true;
}
```

?> 上面是一个方法的所有的配置。可以配置是否打印日志[`logInApp()`]， 以及输出的时间的格和方法的名字配置。

## 五、联系方式

🐘

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="pic/image-20220516083922821.png" width="100px;" alt="thanhtoan1196"/>
      </a>
      <br />
      <span>微信</span>
    </td>
    <td align="center">
      <a href="#">
        <img src="pic/README/image-20221124084524936.png" width="100px;" alt="memset0"/>
      </a>
      <br />
      <span>微信公众号</span>
    </td>
  </tr>
</table>

!> 以上就是 `easy-log` 组件的全部功能，由于作者水平有限，肯定会存在需要歧义的地方，如果你有任何的疑问，都可以联系本作者。同时也欢迎关注《代码废柴》公众号。

**{docsify-updated}** 