package vip.breakpoint.dto;

import vip.breakpoint.enums.ResCodeEnum;

import java.io.Serializable;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/05
 * 欢迎关注公众号:代码废柴
 */
public class RemoteResult implements Serializable {

    private static final long serialVersionUID = -1824940381771195878L;

    private int code;// 返回的操作码  200：说明操作成功  500：说明操作失败
    private String message; // 返回的信息描述
    private Object data; // 响应的数据的基本返回

    public RemoteResult() {
    }

    public RemoteResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // return ok ： 推荐使用
    public static <D> RemoteResult createOK(D data) {
        // return ok
        return createOK("操作成功", data);
    }

    public static <D> RemoteResult createOK(String message, D data) {
        return createResult(ResCodeEnum.SUCCESS, message, data);
    }

    public static <D> RemoteResult createFail(String message, D data) {
        return createResult(ResCodeEnum.FAIL, message, data);
    }

    // 推荐使用这个
    public static <D> RemoteResult createFail(D data) {
        return createFail("操作失败", data);
    }

    public static <D> RemoteResult createResult(ResCodeEnum retCodeConstant, String message, D data) {
        return new RemoteResult(retCodeConstant.getCode(), message, data);
    }
}
