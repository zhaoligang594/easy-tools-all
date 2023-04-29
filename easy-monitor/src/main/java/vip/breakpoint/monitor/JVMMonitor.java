package vip.breakpoint.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author : breakpoint/zlgtop@163.com
 * create on 2023/01/12
 * 欢迎关注公众号:代码废柴
 */
public class JVMMonitor {

    public static Object getJVMInfo() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        return null;
    }
}
