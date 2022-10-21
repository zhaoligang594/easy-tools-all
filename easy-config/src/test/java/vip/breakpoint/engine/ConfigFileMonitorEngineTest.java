package vip.breakpoint.engine;

import org.junit.jupiter.api.Test;

/**
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
class ConfigFileMonitorEngineTest {

    @Test
    public void test01() throws Exception {
        ConfigFileMonitorEngine engine = new ConfigFileMonitorEngine();
        engine.setMonitorDefaultClassPath();
        while (true) {
            Thread.sleep(50000);
        }
    }
}