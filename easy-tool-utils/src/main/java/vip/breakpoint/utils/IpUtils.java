package vip.breakpoint.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public class IpUtils {

    // 获取到我们真实的IP
    public static String getRealIpAddr(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("X-Real-IP");
        if (!EasyStringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!EasyStringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
