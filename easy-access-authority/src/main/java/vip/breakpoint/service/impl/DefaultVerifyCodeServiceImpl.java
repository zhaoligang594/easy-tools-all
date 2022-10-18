package vip.breakpoint.service.impl;

import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.service.VerifyCodeService;

/**
 * 验证码的服务
 *
 * @author : breakpoint
 * create on 2022/10/18
 * 欢迎关注公众号 《代码废柴》
 */
public class DefaultVerifyCodeServiceImpl implements VerifyCodeService {

    @Override
    public boolean doVerifyCodeCorrect(String verifyCodeKey, String reqVerifyCode) throws EasyToolException {
        //        获取到存储的key
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

        /*  上面给出了例子代码 验证验证码需要自己实现     */

        return true;
    }
}
