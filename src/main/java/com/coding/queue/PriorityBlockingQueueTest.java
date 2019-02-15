package com.coding.queue;

import com.coding.pojo.Person;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * PriorityBlockingQueue 是一个支持优先级的无边界阻塞队列
 * 默认情况下采用自然顺序排列，也可以通过比较器 Comparator 指定排序规则
 */
public class PriorityBlockingQueueTest {

    public static void main(String[] args) {
        PriorityBlockingQueue<Person> queue = new PriorityBlockingQueue<Person>(3, ((o1, o2) -> {
            if (o1.getAge() > o2.getAge())
                return 1;
            else if (o1.getAge() < o2.getAge())
                return -1;
            return 0;
        }));

        queue.put(new Person("a", 3));
        queue.put(new Person("b", 1));
        queue.put(new Person("c", 2));

        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
