package vip.breakpoint.service.impl;

import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.service.AccessLimitService;
import vip.breakpoint.service.UserStoreService;

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

    public DefaultAccessLimitServiceImpl(UserStoreService userStoreService) {
        this.userStoreService = userStoreService;
    }

    @Override
    public boolean isCanAccessByClickLimit(String requestURI, String ip, String Host,
                                           AccessLimit methodAnnotation) {
        return true;
    }

    @Override
    public boolean isVerifyCodeCorrect(String verifyCodeKey, String reqVerifyCode) throws EasyToolException {

//        // 获取到存储的key
//        String verifyCodeKey = "";//RedisUtils.getRedisKey(key);
//        //  验证码的基本操作 1min 时间
//        String verifyCode = "";//(String) valueOperations.get(verifyCodeKey);
//
//        if (EasyStringUtils.isBlank(verifyCode)) {
//            ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
//                    "请刷新验证码");
//            return false;
//        }
//
//        // 检验验证码是否一直
//        String verifyCodeRedis = verifyCode.toLowerCase().trim();
//        String verifyCodeReq = verifyCode1.toLowerCase().trim();
//
//        if (!verifyCodeRedis.equals(verifyCodeReq)) {
//            ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
//                    "验证码不正确");
//            return false;
//        }
        return true;
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
