package com.coding.test;

import java.util.concurrent.Semaphore;

/**
 * ThreadA、ThreadB、ThreadC，ThreadA 用于初始化数据 num， 只有当 num 初始化完成之后再让 ThreadB 和 ThreadC 获取到初始化后的变量 num。
 * <p>
 * 定义一个信号量，该类内部维持了多个线程锁，可以阻塞多个线程，释放多个线程，
 * 线程的阻塞和释放是通过 permit 概念来实现的
 */
public class ThreadCommunication {

    private static int num;//定义一个变量作为数据
    private static Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) {
        ThreadCommunication communication = new ThreadCommunication();
        communication.test1();
    }

    private void test1() {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟耗时操作之后初始化变量 num
                    Thread.sleep(1000);
                    num = 1;
                    //初始化完参数后释放两个permit
                    semaphore.release(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(() -> {
            acquire();
            System.out.println(Thread.currentThread().getName() + "获取到 num 的值为:" + num);
        });

        Thread threadC = new Thread(() -> {
            acquire();
            System.out.println(Thread.currentThread().getName() + "获取到 num 的值为:" + num);
        });

        //同时开启3个线程
        threadA.start();
        threadB.start();
        threadC.start();
    }

    private void acquire() {
        try {
            //获取 permit，如果 semaphore 没有可用的permit则等待，如果有则消耗一个
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
