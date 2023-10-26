package com.itwray.study.advance.thread;

import java.util.concurrent.ThreadFactory;

/**
 * {@link ThreadFactory}实现类
 *
 * @author Wray
 * @since 2023/10/26
 */
public class ThreadFactoryImpl implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);

        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("Uncaught exception occurred in thread " + t.getName() + ": " + e.getMessage());
        });

        thread.setDaemon(false);

        return thread;
    }


}
