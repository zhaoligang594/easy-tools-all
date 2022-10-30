package vip.breakpoint.utils;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/30
 * 欢迎关注公众号:代码废柴
 */
public class MaskUtils {

    public static String getMaskPhone(long phoneNumber) {
        return getMaskPhone(String.valueOf(phoneNumber));
    }

    public static String getMaskPhone(String phoneNumber) {
        return phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static void main(String[] args) {
        System.out.println(getMaskPhone("18201999999"));
    }
}
