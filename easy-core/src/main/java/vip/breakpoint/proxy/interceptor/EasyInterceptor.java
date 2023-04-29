package vip.breakpoint.proxy.interceptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理执行的方法
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2023/02/16
 * 欢迎关注公众号:代码废柴
 */
public interface EasyInterceptor extends InvocationHandler, MethodInterceptor {


    @Override
    default Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.invokeMethod(proxy, method, args);
    }

    @Override
    default Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return this.invokeMethod(o, method, objects);
    }

    /**
     * 执行的方法
     *
     * @param proxy  代理对象
     * @param method 方法
     * @param args   参数
     * @return 返回调用结果
     * @throws Throwable 抛出异常
     */
    Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable;
}

