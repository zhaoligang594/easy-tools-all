package vip.breakpoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.bean.RemoteConfigBean;
import vip.breakpoint.dto.ResponseResult;
import vip.breakpoint.remote.bean.RemoteConfigVo;
import vip.breakpoint.service.LocalConfigService;
import vip.breakpoint.service.RemoteConfigService;

import java.util.List;

/**
 * 配置中心的
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
@RestController
@RequestMapping("/config/center")
public class ConfigCenterController {

    @Autowired
    private RemoteConfigService remoteConfigService;

    @AccessLimit(isLogIn = false, needToken = true)
    @GetMapping("/getRemoteConfigList")
    public ResponseResult<List<RemoteConfigBean>> getRemoteConfigList() {
        return ResponseResult.createOK(remoteConfigService.getRemoteConfigBeanList());
    }

    @AccessLimit(isLogIn = false, needToken = true)
    @PostMapping("/registerClient")
    public ResponseResult<Boolean> registerClient(@RequestBody RemoteConfigBean remoteConfigBean) {
        try {
            remoteConfigService.registerClient(remoteConfigBean);
            return ResponseResult.createOK(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(false);
        }
    }

    @Autowired
    private LocalConfigService localConfigService;

    @AccessLimit(isLogIn = false, needToken = true)
    @PostMapping("/getConfigValue")
    public ResponseResult<RemoteConfigVo> getConfigValue(@RequestParam("configKey") String configKey) {
        RemoteConfigVo remoteConfigVo = localConfigService.getRemoteConfigVo(configKey);
        return ResponseResult.createOK(remoteConfigVo);
    }

    @AccessLimit(isLogIn = false, needToken = true)
    @PostMapping("/getConfigValues")
    public ResponseResult<List<RemoteConfigVo>> getConfigValue(@RequestParam("configKeys") String[] configKeys) {
        List<RemoteConfigVo> remoteConfigVo = localConfigService.getRemoteConfigVo(configKeys);
        return ResponseResult.createOK(remoteConfigVo);
    }
}
