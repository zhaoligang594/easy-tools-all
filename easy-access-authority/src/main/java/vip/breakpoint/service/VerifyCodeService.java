package vip.breakpoint.service;

import vip.breakpoint.exception.EasyToolException;

import java.util.concurrent.TimeUnit;

/**
 * 验证码验证操作服务
 *
 * @author : breakpoint
 * create on 2022/10/18
 * 欢迎关注公众号 《代码废柴》
 */
public interface VerifyCodeService {

    /**
     * 默认超时时间
     */
    Long DEFAULT_TIMEOUT = 3L;

    /**
     * 验证验证码
     *
     * @param reqVerifyCode 验证码
     * @param verifyCodeKey key
     * @return true or false
     * @throws EasyToolException 不满足的时候 可以抛出特定的信息
     */
    boolean doVerifyCodeCorrect(String verifyCodeKey, String reqVerifyCode) throws EasyToolException;


    /**
     * @param verifyCodeKey key
     * @param reqVerifyCode code value
     * @param timeOut       超时时间
     * @param timeUnit      单位
     */
    void storeTheVerifyCode(String verifyCodeKey, String reqVerifyCode, Long timeOut, TimeUnit timeUnit);

    /**
     * 存储验证码信息
     *
     * @param verifyCodeKey key
     * @param reqVerifyCode code value
     */
    default void storeTheVerifyCode(String verifyCodeKey, String reqVerifyCode) {
        storeTheVerifyCode(verifyCodeKey, reqVerifyCode, DEFAULT_TIMEOUT, TimeUnit.MINUTES);
    }
}
