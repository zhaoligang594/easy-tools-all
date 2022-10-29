package vip.breakpoint.demo;

import vip.breakpoint.demo.bean.TestUser;

import java.util.List;

/**
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public class ConfigTest {

    public static void main(String[] args) throws Exception {
        while (true) {
            String integer = StringConfigEnum.TEST.get();
            System.out.println(integer);
            String name = StringConfigEnum.TEST_NAME.get();
            System.out.println(name);
            List<TestUser> list = StringListEnum.TEST.get();
            System.out.println(list);
            TestUser testUser = ObjectConfigEnum.TEST.get();
            System.out.println(testUser);
            Thread.sleep(3000);
        }
    }
}
