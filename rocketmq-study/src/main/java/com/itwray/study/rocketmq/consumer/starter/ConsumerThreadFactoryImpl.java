package com.itwray.study.rocketmq.consumer.starter;

import com.alibaba.fastjson.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final Logger log = LoggerFactory.getLogger(ConsumerThreadFactoryImpl.class);

    public ConsumerThreadFactoryImpl(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, THREAD_PREFIX + "-" + this.threadName + "-" + THREAD_INDEX.getAndIncrement());
        thread.setUncaughtExceptionHandler((t, e) -> {
            if (e instanceof ConsumerBusinessException) {
                log.error("MQ消费消息时业务异常!", e);
            } else if (e instanceof JSONException) {
                log.error("MQ消费消息时JSON序列化失败!!", e);
            } else {
                log.error("MQ消费消息时未知的异常!!!", e);
            }
        });
        thread.setDaemon(false);
        return thread;
    }
}
