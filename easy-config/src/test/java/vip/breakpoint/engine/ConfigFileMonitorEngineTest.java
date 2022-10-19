package vip.breakpoint.engine;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author : breakpoint
 * create on 2022/10/19
 * 欢迎关注公众号 《代码废柴》
 */
class ConfigFileMonitorEngineTest {

    @Test
    public void test01() throws Exception {
        ConfigFileMonitorEngine engine = new ConfigFileMonitorEngine();
        List<String> filePath = engine.getFilePath("classpath:a.properties");
        while (true) {
            Thread.sleep(50000);
        }
    }

}