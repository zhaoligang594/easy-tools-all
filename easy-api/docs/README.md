### 🐘 一、自定义组件API文档

自定义组件是为Java平台语言服务的组件。

目前包含了8大部分：

1. easy-access-authority：主要实现的功能是接口的权限校验，可以使用简单的一个注解就可以实现一个拦截、限流防刷的功能。同时，也整合了Swagger，通过简单的配置就可以使用Swagger API工具。
2. easy-algorithm：主要的功能是常用的基本的算法，目前还在完善中。
3. easy-core：整个项目的核心模块，定义一些基本的操作。
4. easy-excel：基于POI的excel简单的操作，可以实现excel的下载以及文件的解析功能，方便实用。
5. easy-log：无侵入的实现日志的收集功能。可以选择异步以及同步的操作方式。同时用户可以自定义实现kafka的的对接。
6. easy-tool-utils：基本的工具类，目前在完善中。
7. easy-config：纯Java项目，简单的问题动态配置，用于不用重新启动项目就可以实现配置的动态更新。
8. easy-config-spring：spring组件，可以实现动态配置变化而不需要项目的重新启动。

### 🐘 二、版本

#### 2.1 最新版本

```xml
<!-- https://mvnrepository.com/artifact/vip.breakpoint/easy-core -->
<dependency>
    <groupId>vip.breakpoint</groupId>
    <artifactId>easy-core</artifactId>
    <version>2.0.0</version>
</dependency>
```

目前的最新是 `2.0.0` ，基于JDK8，spring 5.X 。

更多组件请访问：[(MVN仓库地址)](https://mvnrepository.com/artifact/vip.breakpoint)

### 🐘 三、组件快速连接

<font color='green' style='font-size:19px'>点击左侧导航，快速找到API文档◀️ 🐘 </font>

  * [1.easy-access-authority](documents/easy-access-authority.md)
  * [2.easy-excel](documents/easy-excel.md)
  * [3.easy-log](documents/easy-log.md)
  * [4.easy-config](documents/easy-config.md)
  * [5.easy-config-spring](documents/easy-config-spring.md)
  * [6.其他公共工具](documents/others.md)


### 🐘 四、联系方式

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

<!--
![image-20220516083922821](pic/image-20220516083922821.png)

![image-20221124084524936](pic/README/image-20221124084524936.png)
 -->


**{docsify-updated}** 