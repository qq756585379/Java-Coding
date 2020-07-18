package com.coding.queue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;

public class DelayQueueTest2 {

    private static DelayQueue<OrderDelay> queue = new DelayQueue<>();

    public static void main(String[] args) {
        Thread productThread = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                produce(i);
            }
        });

        Thread consumThread = new Thread(() -> {
            while (true) {
                try {
                    OrderDelay orderDelay = queue.take();//阻塞
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(currentTime);
                    System.out.printf("现在时间是%s;订单%d过期%n", dateString, orderDelay.getOrderId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        productThread.start();
        consumThread.start();
    }

    private static void produce(int orderId) {
        OrderDelay delay = new OrderDelay();
        delay.setOrderId(orderId);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        delay.setOrderTime(currentTime);
        System.out.printf("现在时间是%s;订单%d加入队列%n", dateString, orderId);
        queue.put(delay);
    }
}

