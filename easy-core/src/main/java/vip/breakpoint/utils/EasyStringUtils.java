package vip.breakpoint.utils;

/**
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public class EasyStringUtils {

    // 判斷是否是 空 的字符串
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

    // 是否不为空
    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }

    // 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
    public static String frontCompWithZero(int sourceData, int formatLength) {
        /*
         * 0 指前面补充零
         * formatLength 字符总长度为 formatLength
         * d 代表为正数。
         */
        return String.format("%0" + formatLength + "d", sourceData);
    }

    // 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
    public static String frontCompWithZero(long sourceData, int formatLength) {
        /*
         * 0 指前面补充零
         * formatLength 字符总长度为 formatLength
         * d 代表为正数。
         */
        return String.format("%0" + formatLength + "d", sourceData);
    }

}
