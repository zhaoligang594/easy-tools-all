package vip.breakpoint.utils;

/**
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public class EasyStringUtils {

    /**
     * 判斷是否是 空 的字符串
     *
     * @param text 字符串 信息
     * @return true or false
     */
    public static boolean isBlank(String text) {
        int strLen;
        if (text == null || (strLen = text.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }
}
