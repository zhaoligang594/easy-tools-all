package vip.breakpoint.demo;

import vip.breakpoint.engine.ConfigFileMonitorEngine;
import vip.breakpoint.enums.FileTypeEnum;

/**
 * @author : breakpoint
 * create on 2022/10/21
 * 欢迎关注公众号 《代码废柴》
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
        ConfigFileMonitorEngine engine = new ConfigFileMonitorEngine();

        engine.setMonitorDefaultClassPath();
        engine.setMonitorFileTypes(FileTypeEnum.PROPERTIES);
        engine.startMonitorEngine();

        while (true) {
            Thread.sleep(50000L);
        }
    }
}
