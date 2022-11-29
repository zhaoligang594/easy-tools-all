package vip.breakpoint.handler;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.handler.support.LoggingMethodInterceptorSupport;
import vip.breakpoint.loghandle.EasyLoggingHandle;

import java.lang.reflect.Method;

/**
 * CGLIB
 *
 * @author :breakpoint/赵立刚
 */
public class LoggingCGlibMethodInterceptor extends LoggingMethodInterceptorSupport implements
        MethodInterceptor, EasyLogInterceptor {


    public LoggingCGlibMethodInterceptor(ObjectMethodDefinition methodDefinition, Object target,
                                         EasyLoggingHandle easyLoggingHandle) {
        super(methodDefinition, target, easyLoggingHandle);
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects,
                            MethodProxy methodProxy) throws Throwable {
        return super.invokeMethod(o, method, objects);
    }

    @Override
    public Object getRealBean() {
        return unProxyDelegate;
    }
}
