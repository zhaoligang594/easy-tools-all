package vip.breakpoint.log;

import vip.breakpoint.log.adaptor.ConsoleLoggerImpl;
import vip.breakpoint.log.adaptor.DelegateLoggerImpl;
import vip.breakpoint.log.adaptor.Logger;

import java.lang.reflect.Method;

/**
 * 日志组件工厂
 *
 * @author 赵立刚
 * Created on 2021-02-05
 */
public class WebLogFactory {

    /**
     * 获取相应的日志组件
     *
     * @param clazz 日志记录类
     * @return 日志操作实例
     */
    public static Logger getLogger(Class<?> clazz) {
        try {
            Class<?> slf4jFactoryClazz = Class.forName("org.slf4j.LoggerFactory");
            Method getLogger = slf4jFactoryClazz.getMethod("getLogger", Class.class);
            Object invoke = getLogger.invoke(slf4jFactoryClazz, clazz);
            return new DelegateLoggerImpl(invoke, clazz, LoggingLevel.INFO);
        } catch (Exception e) {
            //  e.printStackTrace();
            // nothing to do
        }
        return new ConsoleLoggerImpl(clazz, LoggingLevel.INFO);
    }
}
