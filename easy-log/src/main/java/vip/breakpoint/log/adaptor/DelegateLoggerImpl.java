package vip.breakpoint.log.adaptor;

import vip.breakpoint.log.LoggingLevel;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 赵立刚
 * Created on 2021-02-05
 */
public class DelegateLoggerImpl extends LoggerSupport {

    private final Object delegate;

    private final Class<?> clazz;

    private final LoggingLevel level;

    private static Map<String, Method> methodMap = new ConcurrentHashMap<String, Method>();

    public DelegateLoggerImpl(Object delegate, Class<?> clazz, LoggingLevel level) {
        this.delegate = delegate;
        this.clazz = clazz;
        this.level = level;
        this.doOtherThing(delegate);
    }

    private void doOtherThing(Object delegate) {
        Class<?> delegateClazz = delegate.getClass();
        try {
            // trace
            methodMap.put("isTraceEnabled", delegateClazz.getMethod("isTraceEnabled"));
            methodMap.put("trace1", delegateClazz.getMethod("trace", String.class));
            methodMap.put("trace2", delegateClazz.getMethod("trace", String.class, Object.class));
            methodMap.put("trace3", delegateClazz.getMethod("trace", String.class, Object.class, Object.class));
            methodMap.put("trace4", delegateClazz.getMethod("trace", String.class, Object[].class));
            methodMap.put("trace5", delegateClazz.getMethod("trace", String.class, Throwable.class));

            // debug
            methodMap.put("isDebugEnabled", delegateClazz.getMethod("isDebugEnabled"));
            methodMap.put("debug1", delegateClazz.getMethod("debug", String.class));
            methodMap.put("debug2", delegateClazz.getMethod("debug", String.class, Object.class));
            methodMap.put("debug3", delegateClazz.getMethod("debug", String.class, Object.class, Object.class));
            methodMap.put("debug4", delegateClazz.getMethod("debug", String.class, Object[].class));
            methodMap.put("debug5", delegateClazz.getMethod("debug", String.class, Throwable.class));

            // info
            methodMap.put("isInfoEnabled", delegateClazz.getMethod("isInfoEnabled"));
            methodMap.put("info1", delegateClazz.getMethod("info", String.class));
            methodMap.put("info2", delegateClazz.getMethod("info", String.class, Object.class));
            methodMap.put("info3", delegateClazz.getMethod("info", String.class, Object.class, Object.class));
            methodMap.put("info4", delegateClazz.getMethod("info", String.class, Object[].class));
            methodMap.put("info5", delegateClazz.getMethod("info", String.class, Throwable.class));

            // warn
            methodMap.put("isWarnEnabled", delegateClazz.getMethod("isWarnEnabled"));
            methodMap.put("warn1", delegateClazz.getMethod("warn", String.class));
            methodMap.put("warn2", delegateClazz.getMethod("warn", String.class, Object.class));
            methodMap.put("warn3", delegateClazz.getMethod("warn", String.class, Object.class, Object.class));
            methodMap.put("warn4", delegateClazz.getMethod("warn", String.class, Object[].class));
            methodMap.put("warn5", delegateClazz.getMethod("warn", String.class, Throwable.class));

            // error
            methodMap.put("isErrorEnabled", delegateClazz.getMethod("isErrorEnabled"));
            methodMap.put("error1", delegateClazz.getMethod("error", String.class));
            methodMap.put("error2", delegateClazz.getMethod("error", String.class, Object.class));
            methodMap.put("error3", delegateClazz.getMethod("error", String.class, Object.class, Object.class));
            methodMap.put("error4", delegateClazz.getMethod("error", String.class, Object[].class));
            methodMap.put("error5", delegateClazz.getMethod("error", String.class, Throwable.class));

        } catch (Exception e) {
            throw new IllegalArgumentException("出现严重异常，服务不能正常的启动，检查日志文件，有一些方法不存在");
        }
    }

    @Override
    public String getName() {
        return clazz.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        try {
            Method isTraceEnabled = methodMap.get("isTraceEnabled");
            return (Boolean) isTraceEnabled.invoke(delegate);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void trace(String var1) {
        // trace1
        try {
            Method trace = methodMap.get("trace1");
            trace.invoke(delegate, var1);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void trace(String var1, Object var2) {
        try {
            Method trace = methodMap.get("trace2");
            trace.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void trace(String var1, Object var2, Object var3) {
        try {
            Method trace = methodMap.get("trace3");
            trace.invoke(delegate, var1, var2, var3);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void trace(String var1, Object... var2) {
        try {
            Method trace = methodMap.get("trace4");
            trace.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void trace(String var1, Throwable var2) {
        try {
            Method trace = methodMap.get("trace5");
            trace.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean isDebugEnabled() {
        try {
            Method isDebugEnabled = methodMap.get("isDebugEnabled");
            return (Boolean) isDebugEnabled.invoke(delegate);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void debug(String var1) {
        try {
            Method debug = methodMap.get("debug1");
            debug.invoke(delegate, var1);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void debug(String var1, Object var2) {
        try {
            Method debug = methodMap.get("debug2");
            debug.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void debug(String var1, Object var2, Object var3) {
        try {
            Method debug = methodMap.get("debug3");
            debug.invoke(delegate, var1, var2, var3);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void debug(String var1, Object... var2) {
        try {
            Method debug = methodMap.get("debug4");
            debug.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void debug(String var1, Throwable var2) {
        try {
            Method debug = methodMap.get("debug5");
            debug.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean isInfoEnabled() {
        try {
            Method isInfoEnabled = methodMap.get("isInfoEnabled");
            return (Boolean) isInfoEnabled.invoke(delegate);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void info(String var1) {
        try {
            Method info = methodMap.get("info1");
            info.invoke(delegate, var1);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void info(String var1, Object var2) {
        try {
            Method info = methodMap.get("info2");
            info.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void info(String var1, Object var2, Object var3) {
        try {
            Method info = methodMap.get("info3");
            info.invoke(delegate, var1, var2, var3);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void info(String var1, Object... var2) {
        try {
            Method info = methodMap.get("info4");
            info.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void info(String var1, Throwable var2) {
        try {
            Method info = methodMap.get("info5");
            info.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean isWarnEnabled() {
        try {
            Method isWarnEnabled = methodMap.get("isWarnEnabled");
            return (Boolean) isWarnEnabled.invoke(delegate);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void warn(String var1) {
        try {
            Method warn = methodMap.get("warn1");
            warn.invoke(delegate, var1);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void warn(String var1, Object var2) {
        try {
            Method warn = methodMap.get("warn2");
            warn.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void warn(String var1, Object... var2) {
        try {
            Method warn = methodMap.get("warn4");
            warn.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void warn(String var1, Object var2, Object var3) {
        try {
            Method warn = methodMap.get("warn3");
            warn.invoke(delegate, var1, var2, var3);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void warn(String var1, Throwable var2) {
        try {
            Method warn = methodMap.get("warn5");
            warn.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean isErrorEnabled() {
        try {
            Method isErrorEnabled = methodMap.get("isErrorEnabled");
            return (Boolean) isErrorEnabled.invoke(delegate);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void error(String var1) {
        try {
            Method error = methodMap.get("error1");
            error.invoke(delegate, var1);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void error(String var1, Object var2) {
        try {
            Method error = methodMap.get("error2");
            error.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void error(String var1, Object var2, Object var3) {
        try {
            Method error = methodMap.get("error3");
            error.invoke(delegate, var1, var2, var3);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void error(String var1, Object... var2) {
        try {
            Method error = methodMap.get("error4");
            error.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void error(String var1, Throwable var2) {
        try {
            Method error = methodMap.get("error5");
            error.invoke(delegate, var1, var2);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
