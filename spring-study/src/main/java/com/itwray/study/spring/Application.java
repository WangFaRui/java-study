package com.itwray.study.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring Application
 *
 * @author Wray
 * @since 2023/10/24
 */
public class Application {

    private volatile boolean isAwait = true;

    public static void main(String[] args) {
        Application application = new Application();
        application.startup();
    }

    public void startup() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("com.itwray.study.spring");
        applicationContext.refresh();
        this.startDaemonAwaitThread();
    }

    public void stopAwait() {
        this.isAwait = false;
    }

    private void startDaemonAwaitThread() {
        Thread awaitThread = new Thread("container-" + (Thread.currentThread().getName())) {

            @Override
            public void run() {
                while (isAwait) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            }

        };
        awaitThread.setContextClassLoader(getClass().getClassLoader());
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
