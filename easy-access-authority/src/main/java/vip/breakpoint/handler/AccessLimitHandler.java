package vip.breakpoint.handler;

import org.springframework.lang.NonNull;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.enums.ResCodeEnum;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.service.AccessLimitService;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.IpUtils;
import vip.breakpoint.utils.TokenUtils;
import vip.breakpoint.utils.browser.ExploreWriteUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AccessLimit注解的处理类
 *
 * @author : breakpoint
 * create on 2022/10/17
 * 欢迎关注公众号 《代码废柴》
 */
public class AccessLimitHandler {

    // 拦截服务类
    private AccessLimitService accessLimitService;

    public void setAccessLimitService(AccessLimitService accessLimitService) {
        this.accessLimitService = accessLimitService;
    }

    public boolean doAccessLimitHandler(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                        AccessLimit methodAnnotation) throws Exception {
        // 判断接口的可用性
        if (!methodAnnotation.enable()) {
            //接口不可用的操作
            ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                    "该接口不可用");
            return false;
        }
        // 限流防刷
        if (methodAnnotation.enableClickLimit()) {
            String requestURI = request.getRequestURI();
            String ip = IpUtils.getRealIpAddr(request);
            String host = request.getHeader("Host");
            if (!accessLimitService.isCanAccessByClickLimit(requestURI, ip, host, methodAnnotation)) {
                ExploreWriteUtils.writeMessage(ResCodeEnum.TO_MANNY_REQUEST, request, response, "操作过于频繁");
                return false;
            }
        }
        /*    限流防刷操作  end   */
        // 验证验证码是否正确
        if (methodAnnotation.isVerifyCode()) {
            String key = request.getParameter("verifyCodeKey");
            if (EasyStringUtils.isBlank(key)) {
                ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                        "请求参数中没有 verifyCodeKey");
                return false;
            }
            String reqVerifyCode = request.getParameter("verifyCode");// 提交过来的验证码
            if (EasyStringUtils.isBlank(reqVerifyCode)) {
                ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                        "请填写验证码");
                return false;
            }
            try {
                if (!accessLimitService.isVerifyCodeCorrect(key, reqVerifyCode)) {
                    ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                            "验证码不正确");
                    return false;
                }
            } catch (EasyToolException e) {
                ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                        e.getMessage());
                return false;
            }
        }
        /*验证验证码是否正确  end*/
        // 登录验证
        if (methodAnnotation.isLogIn()) {
            String tokenKey = TokenUtils.getTokenFromHeaderOrRequestParamOrCookie(request);
            if (null == tokenKey || tokenKey.trim().equals("")) {
                ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                        "您没有登录，无法操作");
                return false;
            }
            if (!accessLimitService.checkUserLogin(tokenKey)) {
                ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                        "您没有登录，无法操作");
                return false;
            }
        }
        // 没有拦截 返回可以执行
        return true;
    }
}
