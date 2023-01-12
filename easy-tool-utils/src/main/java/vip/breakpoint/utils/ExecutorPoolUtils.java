package vip.breakpoint.utils;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 线程池的操作工具
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
public class ExecutorPoolUtils {

    private static final class ExecutorServiceLazy {
        private static final ExecutorService DEFAULT_EXECUTOR_SERVICE =
                ExecutorServiceUtils.getExecutorService(() -> 10, () -> 50, () -> 200L,
                        TimeUnit.MILLISECONDS, 50);
    }

    // 使用默认的线程池操作
    public static <T> T getObjectWithTimeOut(long timeOut,
                                             Supplier<T> resultSupplier) throws Exception {
        return getObjectWithTimeOut(ExecutorServiceLazy.DEFAULT_EXECUTOR_SERVICE, timeOut, resultSupplier);
    }

    /**
     * 获取返回值结果
     *
     * @param executorService 线程池
     * @param timeOut         超时时间单位 ms
     * @param resultSupplier  结果提供器
     * @param <T>             返回类型
     * @return 返回的结果
     * @throws Exception 异常的抛出 需要用户自己处理
     */
    public static <T> T getObjectWithTimeOut(ExecutorService executorService, long timeOut,
                                             Supplier<T> resultSupplier) throws Exception {
        Future<T> result = executorService.submit(resultSupplier::get);
        return result.get(timeOut, TimeUnit.MILLISECONDS);
    }

    public static <T> Future<T> getTaskFuture(ExecutorService executorService,
                                              Supplier<T> resultSupplier) {
        return executorService.submit(resultSupplier::get);
    }

    public static <T> Future<T> getTaskFuture(Supplier<T> resultSupplier) {
        return getTaskFuture(ExecutorServiceLazy.DEFAULT_EXECUTOR_SERVICE, resultSupplier);
    }

}
