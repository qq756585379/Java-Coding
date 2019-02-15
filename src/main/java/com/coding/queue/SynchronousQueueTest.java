package com.coding.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue 实际上不是一个真正的队列
 * 因为它并不能存储元素，因此 put 和 take 会一直阻塞当前线程，每一个插入操作都必须等待另一个线程的删除操作
 */
public class SynchronousQueueTest {

    public static void main(String[] args) {
        SynchronousQueue<String> syncQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                syncQueue.put("a");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(syncQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
