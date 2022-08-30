package vip.breakpoint.utils;


import vip.breakpoint.exception.EasyToolException;

/**
 * 检验一些相关的数据
 */
public abstract class LocalVerify {

    // 邮箱的正则
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
    // 密码的正则
    private static final String PASSWROD_REGEX = "^[a-zA-Z0-9]{6,15}$";
    private static final String DATE_REGEX = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";

    // 校验日期
    public static boolean verifyDate(String dateStr) {
        if (null == dateStr || "".equals(dateStr)) {
            return false;
        } else if (dateStr.matches(DATE_REGEX)) {
            return true;
        }
        return false;
    }

    // 验证邮箱
    public static boolean verifyEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        } else if (email.matches(EMAIL_REGEX)) {
            return true;
        }
        return false;
    }

    // 验证密码
    public static boolean verifyPassword(String password) {
        if (null == password || "".equals(password)) {
            return false;
        } else if (password.matches(PASSWROD_REGEX)) {
            return true;
        }
        return false;

    }

    public static void verifyString(String str, String message) throws EasyToolException {
        if (EasyStringUtils.isBlank(str)) {
            throw new EasyToolException(message + " 不能为空 ");
        }
    }

    public static void verifyObject(Object o, String message) throws EasyToolException {
        if (null == o) {
            throw new EasyToolException(message + " is null ");
        }
    }

    // 检验是否为null  ""
    public static void verifyStringIsNotNull(String... strs) throws EasyToolException {
        if (null != strs && strs.length > 0) {
            for (String str : strs) {
                if (EasyStringUtils.isBlank(str)) {
                    throw new EasyToolException("请求的参数存在空值");
                }
            }
        }
    }
}
