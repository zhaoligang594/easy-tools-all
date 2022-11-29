package vip.breakpoint.remote;

import vip.breakpoint.remote.bean.ConfigChangeVo;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/27
 * 欢迎关注公众号:代码废柴
 */
public interface ConfigChangeService {

    /**
     * 更新配置的操作
     *
     * @param vo 更细的配置
     * @return 是否操作成功
     */
    boolean doChangeConfig(ConfigChangeVo vo);
}
