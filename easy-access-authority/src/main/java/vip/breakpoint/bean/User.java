package vip.breakpoint.bean;

import java.util.Objects;

/**
 * @author : breakpoint
 * create on 2022/09/07
 * 欢迎关注公众号 《代码废柴》
 */
public class User<T> {
    /**
     * 登录的token
     */
    private String token;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户自定义信息
     */
    private T otherMessage;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public T getOtherMessage() {
        return otherMessage;
    }

    public void setOtherMessage(T otherMessage) {
        this.otherMessage = otherMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User<?> user = (User<?>) o;
        return Objects.equals(token, user.token) && Objects.equals(username, user.username)
                && Objects.equals(otherMessage, user.otherMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, username, otherMessage);
    }
}
