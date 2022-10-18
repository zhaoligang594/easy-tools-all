package vip.breakpoint.service;

import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.exception.EasyToolException;

/**
 * 拦截器的业务
 *
 * @author : breakpoint
 * create on 2022/10/16
 * 欢迎关注公众号 《代码废柴》
 */
public interface AccessLimitService {

    /**
     * 判断是否可以进行请求通过
     *
     * @param requestURI       request.getRequestURI();
     * @param ip               request IP
     * @param host             request.getHeader("Host");
     * @param methodAnnotation 注解信息
     * @return true or false
     */
    boolean isCanAccessByClickLimit(String requestURI, String ip, String host, AccessLimit methodAnnotation);

    /**
     * 验证验证码正确性
     *
     * @param verifyCodeKey 验证码的KEY
     * @param reqVerifyCode 用户传递过来的验证信息
     * @return
     */
    boolean isVerifyCodeCorrect(String verifyCodeKey, String reqVerifyCode) throws EasyToolException;

    /**
     * 校验用户是否登录
     *
     * @param tokenKey 用户token
     * @return
     */
    boolean checkUserLogin(String tokenKey);
}
