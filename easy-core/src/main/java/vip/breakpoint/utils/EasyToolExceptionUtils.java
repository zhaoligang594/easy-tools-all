package vip.breakpoint.utils;

import vip.breakpoint.exception.EasyToolException;

/**
 * EasyToolExceptionUtils
 *
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public class EasyToolExceptionUtils {

    public static EasyToolException createException(String message) {
        return new EasyToolException(message);
    }

    public static EasyToolException createException(String message, Throwable e) {
        return new EasyToolException(message, e);
    }

    public static EasyToolException createException(Throwable e) {
        return new EasyToolException(e);
    }
}
