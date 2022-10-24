package vip.breakpoint.log.adaptor;

/**
 * @author 赵立刚
 * Created on 2021-02-05
 */
public abstract class LoggerSupport implements Logger {

    protected String getPrintMessage(String prefix, String printStr) {
        return String.format("%s %s : %s", ROOT_LOGGER_NAME, prefix, printStr);
    }

    protected StringBuilder getPrintStringBuilder(Object... vars) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : vars) {
            sb.append(obj.toString()).append(" ");
        }
        return sb;
    }

    protected String getPrintStr(Object... vars) {
        return this.getPrintStringBuilder(vars).toString();
    }
}
