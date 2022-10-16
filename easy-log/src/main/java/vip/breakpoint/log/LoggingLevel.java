package vip.breakpoint.log;


/**
 * @author 赵立刚
 * Created on 2021-02-05
 */
public enum LoggingLevel {
    ERROR(EventConstants.ERROR_INT, "ERROR"),
    WARN(EventConstants.WARN_INT, "WARN"),
    INFO(EventConstants.INFO_INT, "INFO"),
    DEBUG(EventConstants.DEBUG_INT, "DEBUG"),
    TRACE(EventConstants.TRACE_INT, "TRACE");

    private final int levelInt;

    private final String levelStr;

    LoggingLevel(int i, String s) {
        levelInt = i;
        levelStr = s;
    }

    public int toInt() {
        return levelInt;
    }

    /**
     * Returns the string representation of this Level.
     */
    public String toString() {
        return levelStr;
    }

}
