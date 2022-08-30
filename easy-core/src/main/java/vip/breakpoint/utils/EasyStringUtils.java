package vip.breakpoint.utils;

/**
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public class EasyStringUtils {

    public static boolean isBlank(String text) {
        return null == text || "".equals(text.trim());
    }

    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }
}
