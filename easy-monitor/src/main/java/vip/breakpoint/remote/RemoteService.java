package vip.breakpoint.remote;

import vip.breakpoint.bean.RemoteConfigBean;
import vip.breakpoint.remote.bean.RemoteConfigVo;

/**
 * 远端服务 服务之间的通信
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/05
 * 欢迎关注公众号:代码废柴
 */
public interface RemoteService {

    /**
     * 校验客户端的状态 是否在线
     */
    void checkClientStatus();

    /**
     * 校验是否在线
     *
     * @param rcb 配置bean
     * @return 是否在线
     */
    RemoteConfigBean checkStatus(RemoteConfigBean rcb);

    /**
     * 推送远端的消息
     *
     * @param rcv 变更的配置
     */
    void pushChangeConfigToClient(RemoteConfigVo rcv);

}
