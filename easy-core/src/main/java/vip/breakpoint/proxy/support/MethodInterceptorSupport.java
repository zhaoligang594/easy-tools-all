package vip.breakpoint.proxy.support;

import java.lang.reflect.Method;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2023/02/16
 * 欢迎关注公众号:代码废柴
 */
public interface MethodInterceptorSupport {

    /**
     * 方法执行支持类
     *
     * @param proxyDelegate 代理对象 一般不使用这个对象
     * @param realDelegate  真实的对象 调用方法一般使用这个参数
     * @param method        方法
     * @param args          相关参数
     * @return 返回执行结果
     * @throws Throwable 执行过程中的异常
     */
    Object invokeMethod(Object proxyDelegate, Object realDelegate, Method method, Object[] args) throws Throwable;

}
