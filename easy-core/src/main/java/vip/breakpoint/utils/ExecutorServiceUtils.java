package vip.breakpoint.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 定义线程池的操作
 * 获取特定的线程池
 * 默认的拒绝策略是 使用调用线程执行这个任务
 *
 * @author 赵立刚 <zlgtop@163.com>
 * Created on 2021-02-26
 */
public final class ExecutorServiceUtils {

    private ExecutorServiceUtils() {/*  refuse new obj */}

    // 获取线程池的操作
    public static ExecutorService getExecutorService(Supplier<Integer> coreSize,
                                                     Supplier<Integer> maximumPoolSize,
                                                     Supplier<Long> keepAliveTime,
                                                     TimeUnit unit,
                                                     int queueSize) {
        if (queueSize <= 0) throw new IllegalArgumentException("queueSize must be bigger than 0");
        // 获取线程池的操作
        return new ThreadPoolExecutor(coreSize.get(), maximumPoolSize.get(), keepAliveTime.get(), unit,
                new LinkedBlockingQueue<Runnable>(queueSize), new EasyThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    // 获取线程池的操作
    // 专门对于某一个线程的操作
    public static ExecutorService getExecutorService(Supplier<Integer> coreSize,
                                                     Supplier<Integer> maximumPoolSize,
                                                     Supplier<Long> keepAliveTime,
                                                     TimeUnit unit,
                                                     Supplier<String> threadName,
                                                     int queueSize) {
        if (queueSize <= 0) throw new IllegalArgumentException("queueSize must be bigger than 0");
        // 获取线程池的操作
        return new ThreadPoolExecutor(coreSize.get(), maximumPoolSize.get(), keepAliveTime.get(), unit,
                new LinkedBlockingQueue<Runnable>(queueSize), new EasyThreadFactory(threadName.get()),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    // 获取线程池的操作
    // 专门对于某一个线程的操作
    public static ExecutorService getSingleExecutorService(Supplier<Long> keepAliveTime,
                                                           TimeUnit unit,
                                                           Supplier<String> threadName) {
        // 获取线程池的操作
        return new ThreadPoolExecutor(1, 1, keepAliveTime.get(), unit,
                new LinkedBlockingQueue<Runnable>(1), new EasyThreadFactory(threadName.get()),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    // 自定义线程工厂
    static class EasyThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        private final ThreadGroup group;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final String namePrefix;

        EasyThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        EasyThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }

    }
}
