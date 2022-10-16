package vip.breakpoint.factory;

import static vip.breakpoint.executor.WeblogThreadPoolExecutor.executeDoLog;

import com.alibaba.fastjson.JSONObject;
import vip.breakpoint.annotion.WebLogging;
import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.log.LoggingLevel;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.loghandle.EasyLoggingHandle;

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
    private final Object delegate; //真正的对象
    private final EasyLoggingHandle easyLoggingHandle; // 自定义的handle
    protected final Logger logger = WebLogFactory.getLogger(getClass(), LoggingLevel.TRACE);

    public LoggingMethodInterceptorSupport(ObjectMethodDefinition methodDefinition, Object delegate,
                                           EasyLoggingHandle easyLoggingHandle) {
        this.methodDefinition = methodDefinition;
        this.delegate = delegate;
        this.easyLoggingHandle = easyLoggingHandle;
    }

    // real invoke process
    protected Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
        final String methodName = method.getName();
        // return val
        Object resVal = null;
        if (methodDefinition.isHaveMethod(methodName)) {
            WebLogging webLogging = methodDefinition.getWebLoggingByMethod(methodName);
            SimpleDateFormat sdf = new SimpleDateFormat(webLogging.timePattern());
            if (webLogging.loggingInSystem()) {
                String sb = "request params:【" +
                        JSONObject.toJSONString(args) +
                        "】||request method:【" +
                        methodName +
                        "】|| request time :【" +
                        sdf.format(new Date()) +
                        "】";
                logger.info(sb);
            }
            try {
                // 获取方法上面的注解信息
                Annotation[] annotations = method.getAnnotations();
                if (null != easyLoggingHandle) {
                    executeDoLog(() -> {
                        easyLoggingHandle.invokeBefore(methodName, args, annotations);
                    });
                }
                resVal = method.invoke(delegate, args);
                if (webLogging.loggingInSystem()) {
                    String sb = "response params:【" +
                            JSONObject.toJSONString(args) +
                            "】||request method:【" +
                            methodName +
                            "】|| complete time:【" +
                            sdf.format(new Date()) +
                            "】||return val:【" +
                            JSONObject.toJSONString(resVal) +
                            "】";
                    logger.info(sb);
                }
                if (null != easyLoggingHandle) {
                    executeDoLog(resVal, (result) -> {
                        easyLoggingHandle.invokeAfter(methodName, args, result, annotations);
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
                if (webLogging.loggingInSystem()) {
                    String sb = "request params:【" +
                            JSONObject.toJSONString(args) +
                            "】|| method:【" +
                            methodName +
                            "】|| current time:【" +
                            sdf.format(new Date()) +
                            "】" + "exception:【" +
                            JSONObject.toJSONString(throwable.getClass().getName() + ":cause:" + throwable.getMessage()) +
                            "】";
                    logger.error(sb);
                }
                if (null != easyLoggingHandle) {
                    easyLoggingHandle.invokeOnThrowing(method.getName(), args, method.getAnnotations(), throwable);
                }
                // throw exception
                throw throwable;
            }
        } else {
            // 不进行代理的操作
            resVal = method.invoke(delegate, args);
        }
        return resVal;
    }
}
