package vip.breakpoint.factory;

import net.sf.cglib.proxy.Enhancer;
import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.loghandle.EasyLoggingHandle;

import java.lang.reflect.Proxy;

/**
 * 获取代理对象的工厂类
 *
 * @author :breakpoint/赵立刚
 */
public final class LoggingFactory {

    // jdk proxy
    public static Object getLoggingJDKProxyObject(ClassLoader classLoader, ObjectMethodDefinition methodDefinition,
                                                  Object bean, Class<?> targetClass, EasyLoggingHandle easyLoggingHandle) {
        LoggingJDKMethodInterceptor interceptor = new LoggingJDKMethodInterceptor(methodDefinition, bean, easyLoggingHandle);
        return Proxy.newProxyInstance(classLoader, new Class[]{targetClass}, interceptor);
    }

    // cglib proxy
    public static Object getLoggingCGLibProxyObject(ClassLoader classLoader, ObjectMethodDefinition methodDefinition,
                                                    Object bean, Class<?> targetClass, EasyLoggingHandle easyLoggingHandle) {
        LoggingCGlibMethodInterceptor interceptor = new LoggingCGlibMethodInterceptor(methodDefinition, bean, easyLoggingHandle);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(interceptor);
        return enhancer.create();
    }
}
