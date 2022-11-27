package vip.breakpoint.service.impl;

import vip.breakpoint.annontation.EasyConfig;
import vip.breakpoint.cache.CacheFactory;
import vip.breakpoint.cache.TtlCache;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.service.VerifyCodeService;
import vip.breakpoint.utils.EasyStringUtils;

import java.util.concurrent.TimeUnit;

import static vip.breakpoint.config.IntegerConfigEnum.VERIFY_CODE_CACHE_SIZE;

/**
 * 验证码的服务
 *
 * @author : breakpoint
 * create on 2022/10/18
 * 欢迎关注公众号 《代码废柴》
 */
public class DefaultVerifyCodeServiceImpl implements VerifyCodeService {

    @EasyConfig(value = "verify.code.clear:false", isStatic = true)
    private Boolean verifyCodeClearAfterReq;

    private TtlCache<String> ttlCache = null;

    private TtlCache<String> getTtlCache() {
        if (null == ttlCache) {
            synchronized (DefaultVerifyCodeServiceImpl.class) {
                if (null == ttlCache) {
                    ttlCache = CacheFactory.newVersionCacheInstance(VERIFY_CODE_CACHE_SIZE);
                    return ttlCache;
                }
            }
        }
        return ttlCache;
    }

    @Override
    public boolean doVerifyCodeCorrect(String verifyCodeKey, String reqVerifyCode) throws EasyToolException {
        if (EasyStringUtils.isBlank(verifyCodeKey)) {
            throw new EasyToolException("请求参数中，验证码的key不能为空");
        }
        if (EasyStringUtils.isBlank(verifyCodeKey)) {
            throw new EasyToolException("验证码不能为空");
        }
        String candidateCode = getTtlCache().getObject(verifyCodeKey);
        if (EasyStringUtils.isBlank(candidateCode)) {
            throw new EasyToolException("请刷新验证码");
        }
        if (!candidateCode.trim().equalsIgnoreCase(reqVerifyCode.trim())) {
            throw new EasyToolException("验证码不正确");
        }
        if (verifyCodeClearAfterReq) {
            getTtlCache().removeObject(verifyCodeKey);
        }
        return true;
    }

    @Override
    public void storeTheVerifyCode(String verifyCodeKey, String reqVerifyCode, Long timeOut, TimeUnit timeUnit) {
        if (null != timeOut) {
            // 存储验证码的数据
            getTtlCache().putObject(verifyCodeKey, reqVerifyCode, timeOut, timeUnit);
        }
    }
}
