package com.itwray.study.rocketmq.consumer.starter;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 消费者线程工厂实现类
 *
 * @author Wray
 * @since 2023/10/25
 */
public class ConsumerThreadFactoryImpl implements ThreadFactory {

    public static final String THREAD_PREFIX = "Consumer";

    private static final AtomicLong THREAD_INDEX = new AtomicLong(0);

    private final String threadName;

    public ConsumerThreadFactoryImpl(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, THREAD_PREFIX + "-" + this.threadName + "-" + THREAD_INDEX.getAndIncrement());
        thread.setDaemon(false);
        return thread;
    }
}
