package vip.breakpoint.utils;

import vip.breakpoint.utils.bean.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : breakpoint/赵先生
 * create on 2022/10/31
 * 欢迎关注公众号:代码废柴
 */
class MaskUtilsTest {

    @org.junit.jupiter.api.Test
    public void test() {
        System.out.println(MaskUtils.maskPhone("18201999999"));
        System.out.println(MaskUtils.maskEmail("@qq.com"));
        System.out.println(MaskUtils.maskIdCard("211224199509107"));
        System.out.println(MaskUtils.maskIdCard("211224199509107"));
        System.out.println(MaskUtils.mask(new Test("18888888888", "77@163.com", "211224199509157")));
        System.out.println(MaskUtils.maskList(Arrays.asList(new Test("18888888888", "77@163.com",
                "211224199509157"))));
        Map<String, Test> map = new HashMap<>();
        map.put("1", new Test("18888888888", "77@163.com",
                "211224199509157"));
        System.out.println(MaskUtils.maskMapValue(map));
    }

}