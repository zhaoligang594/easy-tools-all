package vip.breakpoint.demo;

/**
 * @author : breakpoint
 * create on 2022/10/23
 * 欢迎关注公众号 《代码废柴》
 */
public class ConfigTest {

    public static void main(String[] args) throws Exception {
        while (true) {
            Integer integer = IntConfigEnum.TEST.get();
            System.out.println(integer);
            Thread.sleep(3000);
        }
    }
}
