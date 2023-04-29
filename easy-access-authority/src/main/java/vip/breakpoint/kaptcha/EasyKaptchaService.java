package vip.breakpoint.kaptcha;

import java.io.IOException;
import java.util.Map;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/25
 * 欢迎关注公众号:代码废柴
 */
public interface EasyKaptchaService {

    // 默认验证码 待选内容
    String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghgklmnpqrstuvwxyz";

    /**
     * 生成验证码
     *
     * @param verifySize 验证码的长度
     * @return 返回验证码的数据
     * @throws IOException 抛出异常
     */
    Map<String, String> generateVerifyCode(int verifySize) throws IOException;
}
