package vip.breakpoint.handler.support;

import static vip.breakpoint.config.ConfigCenter.ENABLE_LOG_IN_APP_KEY;
import static vip.breakpoint.executor.WeblogThreadPoolExecutor.executeDoLog;

import com.alibaba.fastjson.JSONObject;
import vip.breakpoint.annotion.EasyLog;
import vip.breakpoint.config.ConfigCenter;
import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.loghandle.EasyLoggingHandle;
import vip.breakpoint.utils.EasyStringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志中间件的支持类
 * 去掉没有意义的日志输出 防止产生大对象的问题
 *
 * @author :breakpoint/赵立刚
 */
public abstract class LoggingMethodInterceptorSupport {

    private final ObjectMethodDefinition methodDefinition; // 对象的定义

    protected final Object unProxyDelegate; //真正的对象

    private final EasyLoggingHandle easyLoggingHandle; // 自定义的handle

    protected final Logger logger = WebLogFactory.getLogger(getClass());


    public LoggingMethodInterceptorSupport(ObjectMethodDefinition methodDefinition, Object unProxyDelegate,
                                           EasyLoggingHandle easyLoggingHandle) {
        this.methodDefinition = methodDefinition;
        this.unProxyDelegate = unProxyDelegate;
        this.easyLoggingHandle = easyLoggingHandle;
    }

    // real invoke process
    protected Object executeMethod(Object proxy, Method method, Object[] args) throws Throwable {
        // ret value
        Object resVal;
        // 查看是否需要进行被处理
        EasyLog easyLog = methodDefinition.getEasyLogByMethod(method);
        if (null != easyLog) {
            String methodName = easyLog.name();
            if (EasyStringUtils.isBlank(methodName)) {
                methodName = method.getName();
            }
            Boolean enableLogInApp = ConfigCenter.getValue(ENABLE_LOG_IN_APP_KEY, Boolean.class);
            SimpleDateFormat sdf = new SimpleDateFormat(easyLog.timePattern());
            doLogBefore(enableLogInApp, methodName, args, easyLog, sdf);
            try {
                // 获取方法上面的注解信息
                Annotation[] annotations = method.getAnnotations();
                if (null != easyLoggingHandle) {
                    executeDoLog(() -> {
                        easyLoggingHandle.invokeBefore(method.getName(), args, annotations);
                    });
                }
                resVal = method.invoke(unProxyDelegate, args);
                doLogAfter(enableLogInApp, methodName, args, resVal, easyLog, sdf);
                if (null != easyLoggingHandle) {
                    executeDoLog(resVal, (result) -> {
                        easyLoggingHandle.invokeAfter(method.getName(), args, result, annotations);
                    });
                }
            } catch (Exception e) {
                Throwable throwable;
                if (e instanceof InvocationTargetException) {
                    InvocationTargetException targetException = (InvocationTargetException) e;
                    throwable = targetException.getTargetException();
                } else {
                    throwable = e;
                }
                doLogThrowable(enableLogInApp, methodName, args, easyLog, sdf, throwable);
                if (null != easyLoggingHandle) {
                    easyLoggingHandle.invokeOnThrowing(method.getName(), args, method.getAnnotations(), throwable);
                }
                // throw exception
                throw throwable;
            }
        } else {
            resVal = method.invoke(unProxyDelegate, args);
        }

        return resVal;
    }

    private void doLogThrowable(Boolean enableLogInApp, String methodName, Object[] args, EasyLog easyLog, SimpleDateFormat sdf,
                                Throwable throwable) {
        if (null != enableLogInApp && enableLogInApp && easyLog.logInApp()) {
            String sb = "request params:[" +
                    JSONObject.toJSONString(args) +
                    "],method:[" +
                    methodName +
                    "],current time:[" +
                    sdf.format(new Date()) +
                    "]" + " exception:[" +
                    JSONObject.toJSONString(throwable.getClass().getName() + ":cause:"
                            + throwable.getMessage()) + "]";
            logger.error(sb);
        }
    }

    private void doLogAfter(Boolean enableLogInApp, String methodName, Object[] args, Object resVal, EasyLog easyLog,
                            SimpleDateFormat sdf) {
        if (null != enableLogInApp && enableLogInApp && easyLog.logInApp()) {
            String sb = "response params:[" +
                    JSONObject.toJSONString(args) +
                    "],request method:[" +
                    methodName +
                    "], complete time:[" +
                    sdf.format(new Date()) +
                    "],return val:[" +
                    JSONObject.toJSONString(resVal) +
                    "]";
            logger.info(sb);
        }
    }

    private void doLogBefore(Boolean enableLogInApp, String methodName, Object[] args, EasyLog easyLog,
                             SimpleDateFormat sdf) {
        if (null != enableLogInApp && enableLogInApp && easyLog.logInApp()) {
            String sb = "request params:[" +
                    JSONObject.toJSONString(args) +
                    "],request method:[" +
                    methodName +
                    "], request time :[" +
                    sdf.format(new Date()) +
                    "]";
            logger.info(sb);
        }
    }
}
