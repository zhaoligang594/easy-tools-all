package vip.breakpoint.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 从提交上来的数据里面获取我们的token
 * Created by Administrator on 2018/4/29 0029.
 */
public class TokenUtils {

    private static final String TOKEN_NAME = "token";

    // 从请求中获取到我们的用户的Token
    public static String getTokenFromHeaderOrRequestParamOrCookie(HttpServletRequest request) {
        String token = null;
        token = request.getParameter(TOKEN_NAME);
        if (null == token) {
            token = request.getHeader(TOKEN_NAME);
        }
        if (null == token) {
            Cookie[] cookies = request.getCookies();
            if (null != cookies && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(TOKEN_NAME)) {
                        token = cookie.getValue();
                        break;
                    }
                } // end for
            } // end if

        }// end if
        return token;
    } // end getTokenFromHeaderOrRequestParamOrCookie
}
