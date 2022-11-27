package vip.breakpoint.kaptcha;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.breakpoint.annontation.EasyConfig;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.dto.ResponseResult;
import vip.breakpoint.service.VerifyCodeService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/25
 * 欢迎关注公众号:代码废柴
 */
@RestController
@RequestMapping("/easy-verify-code")
public class EasyVerifyCodeController {

    @Resource
    private EasyKaptchaService easyKaptchaService;

    @Resource
    private VerifyCodeService verifyCodeService;

    // 验证码的默认长度
    @EasyConfig(value = "verify.code.length:4", isStatic = true)
    private Integer verifyCodeLength;

    // 默认3分钟
    @EasyConfig(value = "verify.code.timeout:" + (3000 * 60), isStatic = true)
    private Long verifyCodeTimeOut;

    @AccessLimit(isLogIn = false, enableClickLimit = true)
    @GetMapping("/getVerifyCode")
    public Object getVerifyCode() {
        try {
            Map<String, String> returnMap = easyKaptchaService.generateVerifyCode(verifyCodeLength);
            String key = UUID.randomUUID().toString();
            verifyCodeService.storeTheVerifyCode(key, returnMap.get("verifyCode"), verifyCodeTimeOut, TimeUnit.MILLISECONDS);
            Map<String, String> map = new HashMap<>();
            map.put("verifyCodeKey", key);
            map.put("verifyCode", returnMap.get("verifyCodeString"));
            return ResponseResult.createOK(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }
}
