package vip.breakpoint.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.dto.RemoteResult;
import vip.breakpoint.remote.ConfigChangeService;
import vip.breakpoint.remote.bean.RemoteConfigVo;

import javax.annotation.Resource;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/27
 * 欢迎关注公众号:代码废柴
 */
@RestController
@RequestMapping("/remote-config-change")
public class ConfigChangeController {

    @Resource
    private ConfigChangeService configChangeService;

    @AccessLimit(isLogIn = false, needToken = true)
    @RequestMapping(value = "/changeConfig", method = RequestMethod.POST)
    public Object changeConfig(@RequestBody RemoteConfigVo vo) {
        return RemoteResult.createOK(configChangeService.doChangeConfig(vo));
    }
}
