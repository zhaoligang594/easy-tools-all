package vip.breakpoint.cache;

import vip.breakpoint.utils.ExecutorServiceUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 专门清理缓存的线程
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/11/25
 * 欢迎关注公众号:代码废柴
 */
public class TtlClearContext {

    private static final long DEFAULT_INTERVAL_TIMES = 1000L;

    private static volatile boolean running = true;

    private static volatile boolean haveStared = false;

    private static final class ExecutorServiceClass {
        // 单例模式 并且使用的时候再 初始化 节省资源
        private static final ExecutorService executor =
                ExecutorServiceUtils.getExecutorService(() -> 10, () -> 20, () -> 200L,
                        TimeUnit.MILLISECONDS, 200);
    }

    private static final List<TtlCache<?>> ttlCacheList = new CopyOnWriteArrayList<>();

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static void startExecute(long clearIntervalTimes) {
        if (!haveStared) {
            synchronized (TtlClearContext.class) {
                if (!haveStared) {
                    if (clearIntervalTimes < DEFAULT_INTERVAL_TIMES)
                        throw new IllegalArgumentException("clearIntervalTimes must be bigger than "
                                + DEFAULT_INTERVAL_TIMES + "ms");
                    ExecutorServiceClass.executor.execute(() -> {
                        while (running) {
                            Lock readLock = TtlClearContext.lock.readLock();
                            try {
                                if (readLock.tryLock(200, TimeUnit.MILLISECONDS)) {
                                    for (TtlCache<?> ttlCache : ttlCacheList) {
                                        ttlCache.clearExpireData();

                                    }
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                readLock.unlock();
                            }
                            if (!running) {
                                break;
                            }
                            try {
                                Thread.sleep(clearIntervalTimes);
                            } catch (final InterruptedException ignored) {
                                // ignore
                            }
                        }
                    });
                    // started
                    haveStared = true;
                }
            }
        }
    }

    public static <T> void addClearCache(TtlCache<T> ttlCache) {
        startExecute(DEFAULT_INTERVAL_TIMES);
        Lock writeLock = lock.writeLock();
        while (true) {
            try {
                if (writeLock.tryLock(200, TimeUnit.MILLISECONDS)) {
                    ttlCacheList.add(ttlCache);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }
    }
}
