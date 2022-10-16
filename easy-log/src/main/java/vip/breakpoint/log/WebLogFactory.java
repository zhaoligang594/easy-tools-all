package vip.breakpoint.log;

import vip.breakpoint.log.adaptor.ConsoleLoggerImpl;
import vip.breakpoint.log.adaptor.DelegateLoggerImpl;
import vip.breakpoint.log.adaptor.Logger;

import java.lang.reflect.Method;

/**
 * @author 赵立刚
 * Created on 2021-02-05
 */
public class WebLogFactory {

    public static Logger getLogger(Class<?> clazz, LoggingLevel level) {
        try {
            Class<?> slf4jFactoryClazz = Class.forName("org.slf4j.LoggerFactory");
            Method getLogger = slf4jFactoryClazz.getMethod("getLogger", Class.class);
            Object invoke = getLogger.invoke(slf4jFactoryClazz, clazz);
            return new DelegateLoggerImpl(invoke, clazz, level);
        } catch (Exception e) {
            //e.printStackTrace();
            // nothing to do
        }
        return new ConsoleLoggerImpl(clazz, level);
    }

}
