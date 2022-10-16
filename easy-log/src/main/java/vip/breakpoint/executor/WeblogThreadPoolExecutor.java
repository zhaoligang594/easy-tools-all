package vip.breakpoint.executor;

import vip.breakpoint.log.LoggingLevel;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author 赵立刚
 * Created on 2021-02-05
 */
public final class WeblogThreadPoolExecutor {

    private WeblogThreadPoolExecutor() {/*refuse new project*/}

    private static final Logger logger = WebLogFactory.getLogger(WeblogThreadPoolExecutor.class, LoggingLevel.INFO);
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(WeblogThreadPoolExecutor::shutdownHook));
    }

    private static void shutdownHook() {
        logger.info("释放资源");
        executor.shutdown();
    }

    // 执行日志输出
    public static <T> void executeDoLog(T result, Consumer<T> consumer) {
        executor.submit(() -> {
            consumer.accept(result);
        });
    }

    // 执行日志输出
    public static <T> void executeDoLog(Runnable runnable) {
        executor.submit(runnable);
    }

}
