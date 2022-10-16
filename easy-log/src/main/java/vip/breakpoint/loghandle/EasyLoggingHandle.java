package vip.breakpoint.loghandle;

import java.lang.annotation.Annotation;

/**
 * 日志回调
 *
 * @author :breakpoint/赵立刚
 */
public interface EasyLoggingHandle {

    /**
     * before invoke method process
     *
     * @param methodName  is methodName
     * @param methodArgs  is req args
     * @param annotations is annotations
     */
    void invokeBefore(String methodName, Object[] methodArgs, Annotation[] annotations);

    /**
     * after invoke method process
     *
     * @param methodName  is methodName
     * @param methodArgs  is req args
     * @param resVal      is return value
     * @param annotations is annotations
     */
    void invokeAfter(String methodName, Object[] methodArgs, Object resVal, Annotation[] annotations);

    /**
     * when throw exception
     *
     * @param methodName  is methodName
     * @param methodArgs  is req args
     * @param e           is throws exception
     * @param annotations is annotations
     */
    void invokeOnThrowing(String methodName, Object[] methodArgs, Annotation[] annotations, Throwable e) throws Throwable;

}
