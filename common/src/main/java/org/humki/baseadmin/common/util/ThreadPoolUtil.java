package org.humki.baseadmin.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolUtil {

    /**
     * 创建一个单核心单线程的ThreadPoolExecutor
     *
     * @param threadName 线程名称
     * @param queueSize  工作队列大小
     * @return ThreadPoolExecutor实例
     */
    public static ThreadPoolExecutor createSingleExecutor(String threadName, int queueSize) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").build();
        return new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(queueSize), factory);
    }

    /**
     * 创建一个单核心单线程的ThreadPoolExecutor
     * 拒绝策略是 DiscardOldestPolicy 移除
     *
     * @param threadName 线程名称
     * @param queueSize  工作队列大小
     * @return ThreadPoolExecutor实例
     */
    public static ThreadPoolExecutor createSingleExecutorDiscardOldestPolicy(String threadName, int queueSize) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").build();
        return new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(queueSize), factory, new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /**
     * 创建一个单核心允许多线程的ThreadPoolExecutor
     */
    public static ThreadPoolExecutor creatSingleCoreExecutor(String threadName, int maxSize, int workQueueSize) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(threadName + "-thread-%d").build();
        return new ThreadPoolExecutor(1, maxSize, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(workQueueSize), factory);
    }

    /**
     * 创建一个单核心单线程的ScheduledThreadPool
     */
    public static ScheduledExecutorService creatSingleScheduledThreadPool(String threadName) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").build();
        return Executors.newSingleThreadScheduledExecutor(factory);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("thread sleep error", e);
        }
    }

}
