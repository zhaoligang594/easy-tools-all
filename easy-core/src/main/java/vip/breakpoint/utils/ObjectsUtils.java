package vip.breakpoint.utils;

/**
 * @author : breakpoint/赵先生
 * create on 2022/11/05
 * 欢迎关注公众号:代码废柴
 */
public class ObjectsUtils {

    // return the default value if the result is null
    public static <T> T defaultIfNull(T result, T defaultValue) {
        return null == result ? defaultValue : result;
    }
}
