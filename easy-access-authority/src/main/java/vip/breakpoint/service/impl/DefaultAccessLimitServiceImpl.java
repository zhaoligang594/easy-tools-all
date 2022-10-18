package vip.breakpoint.service.impl;

import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.service.AccessLimitService;
import vip.breakpoint.service.ClickLimitService;
import vip.breakpoint.service.UserStoreService;
import vip.breakpoint.service.VerifyCodeService;

/**
 * 用户校验的操作
 *
 * @author : breakpoint
 * create on 2022/10/16
 * 欢迎关注公众号 《代码废柴》
 */
public class DefaultAccessLimitServiceImpl implements AccessLimitService {

    // 用户存储信息
    private final UserStoreService userStoreService;

    private final ClickLimitService clickLimitService;

    private final VerifyCodeService verifyCodeService;

    public DefaultAccessLimitServiceImpl(UserStoreService userStoreService,
                                         ClickLimitService clickLimitService,
                                         VerifyCodeService verifyCodeService) {
        this.userStoreService = userStoreService;
        this.clickLimitService = clickLimitService;
        this.verifyCodeService = verifyCodeService;
    }

    @Override
    public boolean isCanAccessByClickLimit(String requestURI, String ip, String host,
                                           AccessLimit methodAnnotation) {
        return clickLimitService.isCanAccessByClickLimit(requestURI, ip, host, methodAnnotation);
    }

    @Override
    public boolean isVerifyCodeCorrect(String verifyCodeKey, String reqVerifyCode) throws EasyToolException {
        return verifyCodeService.doVerifyCodeCorrect(verifyCodeKey, reqVerifyCode);
    }

    @Override
    public boolean checkUserLogin(String tokenKey) {
        try {
            Object ret = userStoreService.getUserMessageByUserToken(tokenKey);
            return ret != null;
        } catch (EasyToolException e) {
            return false;
        }
    }
}
