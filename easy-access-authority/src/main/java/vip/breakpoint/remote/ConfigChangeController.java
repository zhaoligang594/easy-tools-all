package vip.breakpoint.remote;

import org.springframework.web.bind.annotation.*;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.dto.ResponseResult;
import vip.breakpoint.remote.bean.ConfigChangeVo;

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
    public Object changeConfig(@RequestBody ConfigChangeVo vo) {
        return ResponseResult.createOK(configChangeService.doChangeConfig(vo));
    }
}
