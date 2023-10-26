package com.itwray.study.advance.thread;

/**
 * 基于{@link Thread}的示例
 *
 * @author Wray
 * @since 2023/10/8
 */
public class ThreadDemo {

    public static void main(String[] args) {
        ThreadDemo demo = new ThreadDemo();
        demo.test();
    }

    public void test() {
        ThreadFactoryImpl threadFactory = new ThreadFactoryImpl();
        Thread thread = threadFactory.newThread(this::run);
        thread.start();
    }

    private void run() {
        System.out.println("11");
        throw new RuntimeException("Oops! Something went wrong.");
    }
}
