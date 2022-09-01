package vip.breakpoint.utils;

/**
 * 断言
 *
 * @author : breakpoint
 * create on 2022/09/02
 * 欢迎关注公众号 《代码废柴》
 */
public class AssertUtils {

    /**
     * 判断是否是空
     *
     * @param text        文本
     * @param hintMessage 提示信息
     */
    public static void text(String text, String hintMessage) {
        if (null == text) {
            throw new IllegalArgumentException(hintMessage + " the value is null");
        }
    }

}
