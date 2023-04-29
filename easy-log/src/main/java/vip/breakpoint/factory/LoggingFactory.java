package vip.breakpoint.factory;

import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.handler.LoggingMethodInterceptor;
import vip.breakpoint.loghandle.EasyLoggingHandle;
import vip.breakpoint.proxy.ObjectProxyFactory;

/**
 * 获取代理对象的工厂类
 *
 * @author :breakpoint/赵立刚
 */
public final class LoggingFactory {

    // jdk proxy
    public static Object getLoggingJDKProxyObject(ClassLoader classLoader, ObjectMethodDefinition methodDefinition,
                                                  Object bean, Class<?> targetClass,
                                                  EasyLoggingHandle easyLoggingHandle) {


        LoggingMethodInterceptor interceptor =
                new LoggingMethodInterceptor(methodDefinition, bean, easyLoggingHandle);
        return ObjectProxyFactory.getJDKProxyObject(classLoader, interceptor, targetClass);
    }

    // cglib proxy
    public static Object getLoggingCGLibProxyObject(ClassLoader classLoader, ObjectMethodDefinition methodDefinition,
                                                    Object bean, Class<?> targetClass,
                                                    EasyLoggingHandle easyLoggingHandle) {

        LoggingMethodInterceptor interceptor =
                new LoggingMethodInterceptor(methodDefinition, bean, easyLoggingHandle);
        return ObjectProxyFactory.getCGLibProxyObject(classLoader, interceptor, targetClass);
    }
}
