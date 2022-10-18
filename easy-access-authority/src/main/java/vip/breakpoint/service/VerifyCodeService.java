package vip.breakpoint.service;

import vip.breakpoint.exception.EasyToolException;

/**
 * 验证码验证操作服务
 *
 * @author : breakpoint
 * create on 2022/10/18
 * 欢迎关注公众号 《代码废柴》
 */
public interface VerifyCodeService {

    /**
     * 验证验证码
     *
     * @throws EasyToolException 不满足的时候 可以抛出特定的信息
     */
    boolean doVerifyCodeCorrect(String verifyCodeKey, String reqVerifyCode) throws EasyToolException;
}
