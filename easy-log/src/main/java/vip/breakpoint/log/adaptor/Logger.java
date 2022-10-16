package vip.breakpoint.log.adaptor;

/**
 *
 * @author 赵立刚
 * Created on 2021-02-05
 */
public interface Logger {

    String ROOT_LOGGER_NAME = "WEB_ROOT";

    String getName();

    boolean isTraceEnabled();

    void trace(String var1);

    void trace(String var1, Object var2);

    void trace(String var1, Object var2, Object var3);

    void trace(String var1, Object... var2);

    void trace(String var1, Throwable var2);

    boolean isDebugEnabled();

    void debug(String var1);

    void debug(String var1, Object var2);

    void debug(String var1, Object var2, Object var3);

    void debug(String var1, Object... var2);

    void debug(String var1, Throwable var2);

    boolean isInfoEnabled();

    void info(String var1);

    void info(String var1, Object var2);

    void info(String var1, Object var2, Object var3);

    void info(String var1, Object... var2);

    void info(String var1, Throwable var2);

    boolean isWarnEnabled();

    void warn(String var1);

    void warn(String var1, Object var2);

    void warn(String var1, Object... var2);

    void warn(String var1, Object var2, Object var3);

    void warn(String var1, Throwable var2);

    boolean isErrorEnabled();

    void error(String var1);

    void error(String var1, Object var2);

    void error(String var1, Object var2, Object var3);

    void error(String var1, Object... var2);

    void error(String var1, Throwable var2);

}
