# easy-tools-all

A collection for tools in Java platform

一系列的基于JAVA平台的使用工具集合

目前该项目分为7大模块，分别是：easy-access-authority、easy-algorithm、easy-core、easy-excel、easy-log、easy-tool-utils、easy-config模块。

1. easy-access-authority主要实现的功能是接口的权限校验，可以使用简单的一个注解就可以实现一个拦截的功能。

2. easy-algorithm主要的功能是常用的基本的算法。

3. easy-core整个项目的核心模块，定义一些基本的操作。

4. easy-excel基于POI的excel简单的操作，可以实现excel的下载以及文件的解析功能，方便实用。

5. easy-log无侵入的实现日志的收集功能。可以选择异步以及同步的操作方式。同时用户可以自定义实现kafka的的对接。

6. easy-tool-utils基本的工具类。

7. easy-config简单的问题动态配置，用于不用重新启动项目就可以实现配置的动态更新。

目前每一个模块还没有完善，正在继续的抽丝剥茧，实现更加强大的通用功能。

后期工作，实现功能的闭环工作以及实现业务的重试功能，最后形成一个小小的生态圈。
