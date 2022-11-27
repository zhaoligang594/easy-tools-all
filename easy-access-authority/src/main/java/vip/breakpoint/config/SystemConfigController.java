package vip.breakpoint.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.breakpoint.annotation.AccessLimit;
import vip.breakpoint.dto.ResponseResult;
import vip.breakpoint.supplier.base.PropertiesContextPool;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/25
 * 欢迎关注公众号:代码废柴
 */
@RestController
@RequestMapping("/easy-system")
public class SystemConfigController {

    @AccessLimit(isLogIn = false, enableClickLimit = true)
    @GetMapping("/config")
    public Object getVerifyCode() {
        return ResponseResult.createOK(PropertiesContextPool.getConfigValuesMap());
    }
}
