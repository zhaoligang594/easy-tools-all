package vip.breakpoint.handler;

import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.handler.support.LoggingMethodInterceptorSupport;
import vip.breakpoint.loghandle.EasyLoggingHandle;
import vip.breakpoint.proxy.interceptor.EasyInterceptor;

import java.lang.reflect.Method;

/**
 * JDK的代理
 *
 * @author :breakpoint/赵立刚
 */
public class LoggingMethodInterceptor extends LoggingMethodInterceptorSupport implements EasyInterceptor {


    public LoggingMethodInterceptor(ObjectMethodDefinition methodDefinition,
                                    Object target, EasyLoggingHandle easyLoggingHandle) {
        super(methodDefinition, target, easyLoggingHandle);
    }

    @Override
    public Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
        return super.executeMethod(proxy, method, args);
    }
}
