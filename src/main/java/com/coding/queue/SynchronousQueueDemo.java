package com.coding.queue;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {

    /**
     * 从结果可以看出，put 线程执行 queue.put(1) 后就被阻塞了，只有 take 线程进行了消费，put 线程才可以返回。
     * 可以认为这是一种线程与线程间一对一传递消息的模型。
     */
    public static void main(String[] args) throws InterruptedException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        Thread putThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("put thread start");
                try {
                    queue.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("put thread end");
            }
        });

        Thread takeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("take thread start");
                try {
                    System.out.println("take from putThread: " + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("take thread end");
            }
        });

        putThread.start();
        Thread.sleep(2000);
        takeThread.start();
    }
}
