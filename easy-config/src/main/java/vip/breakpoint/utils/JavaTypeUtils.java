package vip.breakpoint.utils;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/29
 * 欢迎关注公众号:代码废柴
 */
public class JavaTypeUtils {

    /**
     * 是否为基本的数据类型
     *
     * @param clazz 类型
     * @param <T>   类型
     * @return true or false
     */
    public static <T> boolean isPrimitiveType(Class<T> clazz) {
        return clazz == Integer.class
                || clazz == Byte.class
                || clazz == Short.class
                || clazz == Long.class
                || clazz == Double.class
                || clazz == Float.class
                || clazz == Boolean.class
                || clazz == Character.class
                || clazz == String.class;
    }
}
