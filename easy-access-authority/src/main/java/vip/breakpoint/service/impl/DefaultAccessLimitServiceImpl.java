package vip.breakpoint.service.impl;

import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.bean.LoginUserMsg;
import vip.breakpoint.enums.UserRoleEnum;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.service.*;

import java.io.Serializable;

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

    private final UserUriService userUriService;

    public DefaultAccessLimitServiceImpl(UserStoreService userStoreService,
                                         ClickLimitService clickLimitService,
                                         VerifyCodeService verifyCodeService,
                                         UserUriService userUriService) {
        this.userStoreService = userStoreService;
        this.clickLimitService = clickLimitService;
        this.verifyCodeService = verifyCodeService;
        this.userUriService = userUriService;
    }

    @Override
    public boolean isCanAccessByClickLimit(String requestURI, String ip, String host,
                                           AccessLimit methodAnnotation) {
        return clickLimitService.isCanAccessByClickLimit(requestURI, ip, host, methodAnnotation);
    }

    @Override
    public boolean isVerifyCodeCorrect(String verifyCodeKey, String reqVerifyCode)
            throws EasyToolException {
        return verifyCodeService.doVerifyCodeCorrect(verifyCodeKey, reqVerifyCode);
    }

    @Override
    public boolean checkUserLogin(String tokenKey) {
        return null != userStoreService.getUserMessageByUserTokenV2(tokenKey, Serializable.class);
    }

    @Override
    public boolean checkUserRBAC(String tokenKey, String uri) {
        // 获取到用户登录的信息
        LoginUserMsg<Serializable> loginUserMsg =
                userStoreService.getUserMessageByUserTokenV2(tokenKey, Serializable.class);
        if (null != loginUserMsg) {
            UserRoleEnum roleEnum =
                    UserRoleEnum.getEnumByCode(loginUserMsg.getUserRoleCode());
            if (UserRoleEnum.SUPER_ADMIN == roleEnum) {
                return true;
            }
            return userUriService.checkAuthorityByRole(uri, roleEnum)
                    || userUriService.checkAuthorityByUserId(uri, loginUserMsg.getUserIdToken());
        }
        return false;
    }
}
