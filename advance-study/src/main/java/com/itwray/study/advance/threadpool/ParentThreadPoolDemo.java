package com.itwray.study.advance.threadpool;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParentThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100));
        StopWatch watch = new StopWatch("MainTest");
        CompletableFuture[] mainFutureArr = new CompletableFuture[10];
        watch.start();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            CompletableFuture<Void> mainFuture = CompletableFuture.runAsync(() -> {
                System.out.println("当前线程" + Thread.currentThread().getName() + ",---【任务" + finalI + "】开始执行---");
                //模拟获取数据
                List<String> arrayList = getDataFromDB();
                CompletableFuture[] subFutureArr = new CompletableFuture[arrayList.size()];
                for (int j = 0; j < arrayList.size(); j++) {
                    subFutureArr[j] = getFuture(finalI, arrayList.get(j), executorService);
                }
                //等待所有子任务完成
                CompletableFuture.allOf(subFutureArr).join();
                System.out.println("当前线程" + Thread.currentThread().getName() + ",---【任务" + finalI + "】执行完成---");
            }, executorService);
            mainFutureArr[i] = mainFuture;
        }
        //等待所有父任务完成
        CompletableFuture.allOf(mainFutureArr).join();
        watch.stop();
        System.out.println(watch.prettyPrint());
    }

    private static CompletableFuture<Void> getFuture(int finalI, String str, ExecutorService executorService) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("当前线程" + Thread.currentThread().getName() + ",【任务" + finalI + "】开始处理数据=" + str);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    private static List<String> getDataFromDB() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        return arrayList;
    }
}