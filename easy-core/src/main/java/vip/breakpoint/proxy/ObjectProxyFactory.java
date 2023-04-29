package vip.breakpoint.proxy;

import net.sf.cglib.proxy.Enhancer;
import vip.breakpoint.proxy.interceptor.EasyInterceptor;

import java.lang.reflect.Proxy;

/**
 * 代理类的工厂
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2023/02/16
 * 欢迎关注公众号:代码废柴
 */
public class ObjectProxyFactory {

    // jdk proxy
    public static Object getJDKProxyObject(ClassLoader classLoader,
                                           EasyInterceptor easyInterceptor, Class<?> targetClass) {
        return Proxy.newProxyInstance(classLoader, new Class[]{targetClass}, easyInterceptor);
    }

    // cglib proxy
    public static Object getCGLibProxyObject(ClassLoader classLoader,
                                             EasyInterceptor easyInterceptor, Class<?> targetClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(easyInterceptor);
        return enhancer.create();
    }
}
