package vip.breakpoint.log.adaptor;

import vip.breakpoint.log.LoggingLevel;

/**
 * @author 赵立刚
 * Created on 2021-02-05
 */
public class ConsoleLoggerImpl extends LoggerSupport {

    private final Class<?> clazz;

    private final LoggingLevel level;

    public ConsoleLoggerImpl(Class<?> clazz, LoggingLevel level) {
        this.clazz = clazz;
        this.level = level;
    }

    @Override
    public String getName() {
        return clazz.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return LoggingLevel.TRACE.toInt() >= level.toInt();
    }

    @Override
    public void trace(String var1) {
        System.out.println(getPrintMessage(getName(), var1));
    }

    @Override
    public void trace(String var1, Object var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void trace(String var1, Object var2, Object var3) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2, var3)));
    }

    @Override
    public void trace(String var1, Object... var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void trace(String var1, Throwable var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2.getMessage())));
    }

    @Override
    public boolean isDebugEnabled() {
        return LoggingLevel.DEBUG.toInt() >= level.toInt();
    }

    @Override
    public void debug(String var1) {
        System.out.println(getPrintMessage(getName(), var1));
    }

    @Override
    public void debug(String var1, Object var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void debug(String var1, Object var2, Object var3) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2, var3)));
    }

    @Override
    public void debug(String var1, Object... var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void debug(String var1, Throwable var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2.getMessage())));
    }

    @Override
    public boolean isInfoEnabled() {
        return LoggingLevel.INFO.toInt() >= level.toInt();
    }

    @Override
    public void info(String var1) {
        System.out.println(getPrintMessage(getName(), var1));
    }

    @Override
    public void info(String var1, Object var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void info(String var1, Object var2, Object var3) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2, var3)));
    }

    @Override
    public void info(String var1, Object... var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void info(String var1, Throwable var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2.getMessage())));
    }

    @Override
    public boolean isWarnEnabled() {
        return LoggingLevel.WARN.toInt() >= level.toInt();
    }

    @Override
    public void warn(String var1) {
        System.out.println(getPrintMessage(getName(), var1));
    }

    @Override
    public void warn(String var1, Object var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void warn(String var1, Object... var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void warn(String var1, Object var2, Object var3) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2, var3)));
    }

    @Override
    public void warn(String var1, Throwable var2) {
        System.out.println(getPrintMessage(getName(), getPrintStr(var1, var2.getMessage())));
    }

    @Override
    public boolean isErrorEnabled() {
        return LoggingLevel.ERROR.toInt() >= level.toInt();
    }

    @Override
    public void error(String var1) {
        System.err.println(getPrintMessage(getName(), var1));
    }

    @Override
    public void error(String var1, Object var2) {
        System.err.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void error(String var1, Object var2, Object var3) {
        System.err.println(getPrintMessage(getName(), getPrintStr(var1, var2, var3)));
    }

    @Override
    public void error(String var1, Object... var2) {
        System.err.println(getPrintMessage(getName(), getPrintStr(var1, var2)));
    }

    @Override
    public void error(String var1, Throwable var2) {
        System.err.println(getPrintMessage(getName(), getPrintStr(var1, var2.getMessage())));
    }
}
