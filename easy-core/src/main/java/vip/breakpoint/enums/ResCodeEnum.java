package vip.breakpoint.enums;

/**
 * @author : breakpoint
 * create on 2022/08/29
 * 欢迎关注公众号 《代码废柴》
 */
public enum ResCodeEnum {
    SUCCESS(200, "成功"),
    NOT_LOGIN(300, "未登录"),
    EXCEPTION(400, "请求发生异常"),
    FAIL(500, "失败"),
    TO_MANNY_REQUEST(600, "不能在1s内连续请求该接口"),
    ALERT(700, "弹出提示");
    // return code
    private final Integer code;
    // return message
    private final String msg;

    ResCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
