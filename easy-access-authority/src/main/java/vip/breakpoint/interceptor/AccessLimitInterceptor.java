package vip.breakpoint.interceptor;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.TokenUtils;
import vip.breakpoint.utils.browser.ExploreWriteUtils;
import vip.breakpoint.dto.ResponseResult;
import vip.breakpoint.enums.ResCodeEnum;
import vip.breakpoint.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 检验用户登陆以及其他操作  增加接口是否可用的操作
 * Created by Administrator on 2018/4/29 0029.
 */
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {


    public AccessLimitInterceptor() {
    }


    // 拦截器的处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
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
                    String requestURI1 = request.getRequestURI();
                    String ip = IpUtils.getRealIpAddr(request);
                    String Host = request.getHeader("Host");
                    // TODO list
                    boolean isCanpress = true;// 能否继续向下进行执行
//                    String intervalKey = RedisUtils.getRedisKey(Host + ip + requestURI1);
//                    /*    限流防刷操作  start   */
//                    IntervalClass intervalClass = null;
//                    boolean isCanpress = true;// 能否继续向下进行执行
//                    // 取出来的时间
//                    String keyOptValue = (String) valueOperations.get(intervalKey);
//                    if (StringUtils.isEmpty(keyOptValue)) {
//                        // 第一次点击
//                        intervalClass = new IntervalClass();
//                        long currentTime = System.currentTimeMillis();
//                        intervalClass.setFirstClickTime(currentTime);
//                        intervalClass.setPreClickTime(currentTime);
//                        intervalClass.setClickCount(1);
//                    } else {
//                        // 不是第一次点击
//                        intervalClass = JSONObject.parseObject(keyOptValue, IntervalClass.class);
//                        // 获取当前时间
//                        long currentTime = System.currentTimeMillis();
//                        // 点击的间隔的时间
//                        long interval = methodAnnotation.interval();
//                        if ((currentTime - intervalClass.getPreClickTime()) < interval) {
//                            isCanpress = false;
//                        }
//                        // 设置新值
//                        intervalClass.setPreClickTime(currentTime);
//                        intervalClass.setClickCount(intervalClass.getClickCount() + 1);
//                    }
//                    String secondString = JSONObject.toJSONString(intervalClass);
//                    // 10秒自动消失
//                    valueOperations.set(intervalKey, secondString, 10, TimeUnit.SECONDS);

                    // 返回不能继续进行情况
                    if (!isCanpress) {
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

                    // 获取到存储的key
                    String verifyCodeKey = "";//RedisUtils.getRedisKey(key);
                    //  验证码的基本操作 1min 时间
                    String verifyCode = "";//(String) valueOperations.get(verifyCodeKey);

                    if (EasyStringUtils.isBlank(verifyCode)) {
                        ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                                "请刷新验证码");
                        return false;
                    }
                    String verifyCode1 = request.getParameter("verifyCode");// 提交过来的验证码

                    if (EasyStringUtils.isBlank(verifyCode1)) {
                        ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                                "请填写验证码");
                        return false;
                    }
                    // 检验验证码是否一直
                    String verifyCodeRedis = verifyCode.toLowerCase().trim();
                    String verifyCodeReq = verifyCode1.toLowerCase().trim();

                    if (!verifyCodeRedis.equals(verifyCodeReq)) {
                        ExploreWriteUtils.writeMessage(ResCodeEnum.FAIL, request, response,
                                "验证码不正确");
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

                }

            } else {
                //如果没有注解  那么就不让访问
                String referer = request.getHeader("Referer");
                String requestURI = request.getRequestURL().toString();
                // referer.contains("swagger")也是调用的  indexOf(s.toString()) > -1 进行比较的
//                if (!StringUtils.isEmpty(referer) && referer.contains("swagger")) {
                if (!EasyStringUtils.isBlank(referer) && referer.contains("doc.html")) {
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
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //log.info("com.bupt.interceptor.AccessLimitInterceptor.postHandle");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //log.info("com.bupt.interceptor.AccessLimitInterceptor.afterCompletion");
        Object opt_message = request.getAttribute("opt_message");
        //log.info("opt_message:{}", opt_message);
        // 获取到操作的对象
//        SysThreadBean sysThreadBean = SysThreadLocalUtils.INTERCEPTOR_METHOD_THREAD_LOCAL.get();
//        //log.info("sysThreadBean=[{}]", JSON.toJSONString(sysThreadBean));
//        // 是放掉操作对象
//        SysThreadLocalUtils.INTERCEPTOR_METHOD_THREAD_LOCAL.remove();
//        if (response.getStatus() == 500 || response.getStatus() == 404) {
//            if (null != ex) {
//                ExploreWriteUtils.writeMessage(RetCodeConstant.FAIL, request, response, "系统内部错误，请联系管理员 message=" + ex.getMessage());
//            } else {
//                ExploreWriteUtils.writeMessage(RetCodeConstant.FAIL, request, response, "系统内部错误，请联系管理员");
//            }
//            return;
//        }
        super.afterCompletion(request, response, handler, ex);
    }
}
