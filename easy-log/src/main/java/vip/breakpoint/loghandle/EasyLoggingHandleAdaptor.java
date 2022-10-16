package vip.breakpoint.loghandle;

import java.lang.annotation.Annotation;

/**
 * @author :breakpoint/赵立刚
 */
public abstract class EasyLoggingHandleAdaptor implements EasyLoggingHandle {

    @Override
    public void invokeBefore(String methodName, Object[] methodArgs, Annotation[] annotations) {
        // for subclass implements
    }

    @Override
    public void invokeAfter(String methodName, Object[] methodArgs, Object resVal, Annotation[] annotations) {
        // for subclass implements
    }

    @Override
    public void invokeOnThrowing(String methodName, Object[] methodArgs, Annotation[] annotations, Throwable e) throws Throwable {
        // for subclass implements
    }
}
