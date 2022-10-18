package vip.breakpoint.service.impl;

import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.bean.ClickIntervalInfo;
import vip.breakpoint.cache.UserClickCache;
import vip.breakpoint.service.ClickLimitService;

/**
 * @author : breakpoint
 * create on 2022/10/18
 * 欢迎关注公众号 《代码废柴》
 */
public class DefaultClickLimitServiceImpl implements ClickLimitService {


    private String getClickKey(String requestURI, String ip, String host) {
        return String.format("%s_%s_%s", host, ip, requestURI);
    }

    @Override
    public boolean isCanAccessByClickLimit(String requestURI, String ip, String host, AccessLimit methodAnnotation) {
        boolean ret = true;
        String clickKey = getClickKey(requestURI, ip, host);
        ClickIntervalInfo intervalInfo = UserClickCache.get(clickKey);
        if (null == intervalInfo) {
            intervalInfo = new ClickIntervalInfo();
            long l = System.currentTimeMillis();
            intervalInfo.setFirstClickTime(l);
            intervalInfo.setPreClickTime(l);
            intervalInfo.setClickCount(1);
            ret = true;
        } else {
            long l = System.currentTimeMillis();
            if (methodAnnotation.interval() > l - intervalInfo.getPreClickTime()) {
                ret = false;
            }
            intervalInfo.setPreClickTime(l);
            intervalInfo.setClickCount(intervalInfo.getClickCount() + 1);
        }
        UserClickCache.add(clickKey, intervalInfo);
        return ret;
    }
}
