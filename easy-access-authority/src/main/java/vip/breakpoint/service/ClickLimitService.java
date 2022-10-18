package vip.breakpoint.service;

import vip.breakpoint.annotation.AccessLimit;

/**
 * @author : breakpoint
 * create on 2022/10/18
 * 欢迎关注公众号 《代码废柴》
 */
public interface ClickLimitService {


    /**
     * 点击是否触发限流操作
     *
     * @param requestURI       requestURI
     * @param ip               ip
     * @param host             host
     * @param methodAnnotation 方法上面的注解
     * @return true or false
     */
    boolean isCanAccessByClickLimit(String requestURI, String ip, String host, AccessLimit methodAnnotation);
}
