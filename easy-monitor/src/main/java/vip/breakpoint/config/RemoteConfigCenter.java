package vip.breakpoint.config;

import vip.breakpoint.bean.RemoteConfigBean;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 配置的缓存
 *
 * @author : breakpoint/zlgtop@163.com
 * create on 2022/12/04
 * 欢迎关注公众号:代码废柴
 */
public class RemoteConfigCenter {

    /**
     * 注册配置
     */
    private static final List<RemoteConfigBean> remoteConfigBeanList = new CopyOnWriteArrayList<>();

    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public static void addRemoteConfigBean(RemoteConfigBean rcb) {
        if (null != rcb) {
            Lock writeLock = readWriteLock.writeLock();
            while (true) {
                try {
                    if (writeLock.tryLock(200L, TimeUnit.MILLISECONDS)) {
                        remoteConfigBeanList.add(rcb);
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

    public static List<RemoteConfigBean> getLocalRemoteConfigBeanList() {
        Lock readLock = readWriteLock.readLock();
        while (true) {
            try {
                if (readLock.tryLock(200L, TimeUnit.MILLISECONDS)) {
                    return remoteConfigBeanList;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
        }
    }

    public static void addOnlineRemoteConfigBeans(List<RemoteConfigBean> rcbList) {
        if (null != rcbList) {
            Lock writeLock = readWriteLock.writeLock();
            while (true) {
                try {
                    if (writeLock.tryLock(200L, TimeUnit.MILLISECONDS)) {
                        remoteConfigBeanList.clear();
                        remoteConfigBeanList.addAll(rcbList);
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
}
