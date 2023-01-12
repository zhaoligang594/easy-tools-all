package vip.breakpoint.bean;

import java.io.Serializable;

/**
 * 用户登录信息
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/02
 * 欢迎关注公众号:代码废柴
 */
public class LoginUserMsg<T extends Serializable> implements Serializable {

    /**
     * 用户角色的
     */
    private Integer userRoleCode;
    /**
     * 标记用户的唯一ID
     */
    private Long userIdToken;
    /**
     * 用户自定义信息
     */
    private T userMessage;

    public Integer getUserRoleCode() {
        return userRoleCode;
    }

    public void setUserRoleCode(Integer userRoleCode) {
        this.userRoleCode = userRoleCode;
    }

    public T getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(T userMessage) {
        this.userMessage = userMessage;
    }

    public Long getUserIdToken() {
        return userIdToken;
    }

    public void setUserIdToken(Long userIdToken) {
        this.userIdToken = userIdToken;
    }
}
