package vip.breakpoint.utils.base;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public abstract class BaseExplore {

    //设置我们的请求可以跨域请求
    protected static void preSetCommonHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,token,is");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Request-Headers", "Origin, X-Requested-With, content-Type, Accept, Authorization");
        response.setCharacterEncoding("UTF-8");
    }
}
