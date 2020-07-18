package com.coding.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadJoinTest {

    static Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("111111111");
        }
    });

    static Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("222222222");
        }
    });

    static Thread thread3 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("333333333");
        }
    });

    private static void test1() throws InterruptedException {
        thread1.start();
        thread1.join();//主线程休眠，直到线程1结束
        thread2.start();
        thread2.join();//主线程休眠，直到线程2结束
        thread3.start();
        thread3.join();//主线程休眠，直到线程3结束
    }

    // 创建只有一个线程的线程池，能达到排队效果
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static void test() {
        executorService.submit(thread1);
        executorService.submit(thread2);
        executorService.submit(thread3);
        executorService.shutdown();
    }

    /**
     * 如何让线程顺序执行
     */
    public static void main(String[] args) throws InterruptedException {
        test();
    }
}
