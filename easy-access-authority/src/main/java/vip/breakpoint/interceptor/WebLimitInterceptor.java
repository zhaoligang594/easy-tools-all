package vip.breakpoint.interceptor;


import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.enums.ResCodeEnum;
import vip.breakpoint.handler.AccessLimitHandler;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;
import vip.breakpoint.utils.EasyStringUtils;
import vip.breakpoint.utils.ExploreWriteUtils;
import vip.breakpoint.utils.ResponseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static vip.breakpoint.config.StringConfigEnum.ERROR_PATH;
import static vip.breakpoint.config.StringConfigEnum.SERVER_ERROR_PATH;

/**
 * 检验用户登陆以及其他操作  增加接口是否可用的操作
 * Created by Administrator on 2018/4/29 0029.
 */
public class WebLimitInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = WebLogFactory.getLogger(WebLimitInterceptor.class);

    public WebLimitInterceptor() {
        this.uncheckRefer.add("/doc.html");
        this.uncheckURI.add("images");
        this.uncheckURI.add("css");
        this.uncheckURI.add("js");
        this.uncheckURI.add("swagger");
        this.uncheckURI.add("swagger-resources");
        this.uncheckURI.add("webjars");
        this.uncheckURI.add("swagger-ui.html");
        //this.uncheckURI.add("/doc.html");
    }

    // 拦截处理类
    private AccessLimitHandler interceptorHandler;

    public void setHandler(AccessLimitHandler interceptorHandler) {
        this.interceptorHandler = interceptorHandler;
    }

    private final Collection<String> uncheckRefer = new ArrayList<>();
    private final Collection<String> uncheckURI = new ArrayList<>();
    // 可以忽略的请求
    private final Collection<String> ignorePaths = new ArrayList<>();


    public void addIgnorePaths(List<String> ignorePaths) {
        if (null != ignorePaths) {
            this.ignorePaths.addAll(ignorePaths);
        }
    }


    public void setUncheckRefer(Collection<String> uncheckRefer) {
        this.uncheckRefer.addAll(uncheckRefer);
    }

    // 拦截器的处理
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler)
            throws Exception {
        //log.info("preHandle start");
        //设置我们的请求可以跨域请求
        ResponseUtils.preSetCommonHeader(response);
        if (handler instanceof HandlerMethod) {
            // 获取到请求方法的对象
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit methodAnnotation = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (null != methodAnnotation) {
                return interceptorHandler.doAccessLimitHandler(request, response, methodAnnotation);
            }
            String errorPage = SERVER_ERROR_PATH.get();
            if (EasyStringUtils.isBlank(errorPage)) {
                errorPage = ERROR_PATH.get();
            }
            // log.info("error page is :{}", errorPage);
            if (request.getRequestURI().contains(errorPage)) {
                // for error mapping
                return true;
            } else {
                String requestURI = request.getRequestURI();
                // 是否在ignore path中
                if (this.ignorePaths.stream()
                        .filter(Objects::nonNull)
                        .anyMatch(requestURI::startsWith)) {
                    return true;
                }
                //如果没有注解  那么就不让访问
                String referer = request.getHeader("Referer");
                Set<String> unCheckReferList = new HashSet<>(uncheckRefer);
                Set<String> uncheckURIList = new HashSet<>(uncheckURI);

                if (EasyStringUtils.isNotBlank(referer)) {
                    // http://localhost:8080/doc.html-> localhost:8080/doc.html
                    referer = referer.substring(referer.indexOf(":") + 3);
                    // localhost:8080/doc.html -> /doc.html
                    referer = referer.substring(referer.indexOf("/"));
                }

                if (!EasyStringUtils.isBlank(referer)
                        && unCheckReferList.stream().anyMatch(referer::startsWith)
                        && uncheckURIList.stream().anyMatch(requestURI::contains)) {
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
