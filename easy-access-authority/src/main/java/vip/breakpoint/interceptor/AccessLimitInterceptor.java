package vip.breakpoint.interceptor;


import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.exception.EasyToolException;
import vip.breakpoint.service.AccessLimitService;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.TokenUtils;
import vip.breakpoint.utils.browser.ExploreWriteUtils;
import vip.breakpoint.enums.ResCodeEnum;
import vip.breakpoint.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 检验用户登陆以及其他操作  增加接口是否可用的操作
 * Created by Administrator on 2018/4/29 0029.
 */
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    private final AccessLimitService accessLimitService;

    public AccessLimitInterceptor(AccessLimitService accessLimitService) {
        this.accessLimitService = accessLimitService;
        this.uncheckUri.add("doc.html");
    }

    private final Collection<String> uncheckUri = new ArrayList<>();

    public void setUncheckUri(Collection<String> uncheckUri) {
        this.uncheckUri.addAll(uncheckUri);
    }

    // 拦截器的处理
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler)
            throws Exception {
        //log.info("preHandle start");
        //设置我们的请求可以跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,token,is");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Request-Headers", "Origin, X-Requested-With, content-Type, Accept, Authorization");
        response.setCharacterEncoding("UTF-8");

        if (handler instanceof HandlerMethod) {
            // 获取到请求方法的对象
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit methodAnnotation = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (null != methodAnnotation) {
                // 判断接口的可用性
                if (!methodAnnotation.enable()) {
                    //接口不可用的操作
                    ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                            "该接口不可用");
                    return false;
                }
                // 限流防刷
                if (methodAnnotation.isEnableClickLimit()) {
                    String requestURI = request.getRequestURI();
                    String ip = IpUtils.getRealIpAddr(request);
                    String host = request.getHeader("Host");
                    if (!accessLimitService.isCanAccessByClickLimit(requestURI, ip, host, methodAnnotation)) {
                        ExploreWriteUtils.writeMessage(ResCodeEnum.TO_MANNY_REQUEST, request, response,
                                "您操作过于频繁，请间隔1s操作");
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

            } else {
                //如果没有注解  那么就不让访问
                String referer = request.getHeader("Referer");
                Set<String> unCheckReferList = new HashSet<>(uncheckUri);
                if (!EasyStringUtils.isBlank(referer) && unCheckReferList.stream().anyMatch(referer::contains)) {
                    // omit...
                } else {
                    ExploreWriteUtils.writeMessage(ResCodeEnum.ALERT, request, response,
                            "请在该接口方法上使用注解： @AccessLimit()");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                           @NonNull Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
