package vip.breakpoint.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author : breakpoint
 * create on 2022/09/05
 * 欢迎关注公众号 《代码废柴》
 */
class EasyColUtilsTest {

    @Test
    public void test01() {
        // System.out.println(EasyColUtils.isNotEmpty(null));
        System.out.println(EasyColUtils.isNotEmpty(new ArrayList<>()));
        System.out.println(EasyColUtils.isEmpty(new ArrayList<>(null)));
    }

}