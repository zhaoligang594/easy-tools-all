package vip.breakpoint.monitor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.dto.RemoteResult;
import vip.breakpoint.dto.ResponseResult;

/**
 * 监控服务健康转状态的接口
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/27
 * 欢迎关注公众号:代码废柴
 */
@RestController
@RequestMapping("/easy-monitor")
public class EasyMonitorController {

    @AccessLimit(isLogIn = false, needToken = true)
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public RemoteResult status() {
        return RemoteResult.createOK("UP");
    }
}
