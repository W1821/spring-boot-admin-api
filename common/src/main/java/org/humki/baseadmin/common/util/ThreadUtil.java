package org.humki.baseadmin.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author Kael
 * @date 2018/5/23 0023
 */
@Slf4j
public class ThreadUtil {

    /**
     * 创建一个单线程的ThreadPoolExecutor
     *
     * @param threadName 线程名称
     * @param queueSize  工作队列大小
     * @return ThreadPoolExecutor实例
     */
    public static ThreadPoolExecutor createThreadPoolExecutor(String threadName, int queueSize) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").build();
        return new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(queueSize), factory);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("thread sleep error", e);
        }
    }


}
