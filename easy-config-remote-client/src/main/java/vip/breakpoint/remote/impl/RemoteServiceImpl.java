package vip.breakpoint.remote.impl;

import vip.breakpoint.bean.RemoteConfigBean;
import vip.breakpoint.remote.RemoteService;
import vip.breakpoint.remote.bean.RemoteConfigVo;
import vip.breakpoint.service.LocalConfigService;
import vip.breakpoint.utils.ExecutorPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/05
 * 欢迎关注公众号:代码废柴
 */
public class RemoteServiceImpl implements RemoteService {

    private final LocalConfigService localConfigService;

    public RemoteServiceImpl(LocalConfigService localConfigService) {
        this.localConfigService = localConfigService;
    }

    private Long DEFAULT_TIME_OUT = 1000L;

    @Override
    public void checkClientStatus() {
        List<RemoteConfigBean> localConfigBeanList = localConfigService.getLocalConfigBeanList();
        List<Future<RemoteConfigBean>> candidateFutureList = new ArrayList<>();
        List<RemoteConfigBean> onLineHosts = new ArrayList<>();
        for (RemoteConfigBean remoteConfigBean : localConfigBeanList) {
            candidateFutureList.add(ExecutorPoolUtils.getTaskFuture(() -> this.checkStatus(remoteConfigBean)));
        }
        for (Future<RemoteConfigBean> future : candidateFutureList) {
            try {
                RemoteConfigBean remoteConfigBean = future.get(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS);
                if (null != remoteConfigBean) {
                    onLineHosts.add(remoteConfigBean);
                }
            } catch (Exception e) {
                // 出现异常
            }
        }
        // 重新配置
        localConfigService.addOnlineRemoteConfigBeans(onLineHosts);
    }

    @Override
    public RemoteConfigBean checkStatus(RemoteConfigBean rcb) {
        for (int i = 0; i < 3; i++) {
            try {
                return ExecutorPoolUtils.getObjectWithTimeOut(DEFAULT_TIME_OUT, () -> {
                    // todo
                    return rcb;
                });
            } catch (Exception e) {
                // 执行重试
            }
        }
        return null;
    }

    @Override
    public void pushChangeConfigToClient(RemoteConfigVo rcv) {

    }

}
