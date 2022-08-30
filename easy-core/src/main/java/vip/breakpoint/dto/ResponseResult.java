package vip.breakpoint.dto;


import lombok.Data;
import vip.breakpoint.enums.ResCodeEnum;

import java.io.Serializable;

/**
 * 封装的饭互译对象
 *
 * @author :breakpoint/赵立刚
 * create on 2017/11/15
 */
public class ResponseResult<D> implements Serializable {
    private static final long serialVersionUID = -5290436355400748988L;
    private int respCode;// 返回的操作码  200：说明操作成功  500：说明操作失败
    private String message; // 返回的信息描述
    private D data; // 响应的数据的基本返回

    /*  私有构造方法 防止用户自己new 对象   */
    private ResponseResult() {
    }

    private ResponseResult(int respCode, String message, D data) {
        this.respCode = respCode;
        this.message = message;
        this.data = data;
    }

    // return ok ： 推荐使用
    public static <D> ResponseResult<D> createOK(D data) {
        // return ok
        return createOK("操作成功", data);
    }

    public static <D> ResponseResult<D> createOK(String message, D data) {
        return createResult(ResCodeEnum.SUCCESS, message, data);
    }

    public static <D> ResponseResult<D> createFail(String message, D data) {
        return createResult(ResCodeEnum.FAIL, message, data);
    }

    // 推荐使用这个
    public static <D> ResponseResult<D> createFail(D data) {
        return createFail("操作失败", data);
    }

    public static <D> ResponseResult<D> createResult(ResCodeEnum retCodeConstant, String message, D data) {
        return new ResponseResult<>(retCodeConstant.getCode(), message, data);
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
