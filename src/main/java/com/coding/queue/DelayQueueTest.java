package com.coding.queue;

import lombok.Data;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue 是一个支持延迟获取元素的无边界阻塞队列
 * 列头的元素是最先到期的元素，若无元素到期，即使队列有元素，也无法从列头获取元素
 */
@Data
public class DelayQueueTest implements Delayed {

    private long delay; //延迟时间
    private long expire;  //到期时间
    private int number;   //值
    private long now; //创建时间

    public DelayQueueTest(long delay, int number) {
        this.delay = delay;
        this.number = number;
        expire = System.currentTimeMillis() + delay;    //到期时间 = 当前时间+延迟时间
        now = System.currentTimeMillis();
    }

    /**
     * 需要实现的接口，获得延迟时间   用过期时间-当前时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 用于延迟队列内部比较排序
     */
    @Override
    public int compareTo(Delayed o) {
        DelayQueueTest delayQueueTest = (DelayQueueTest) o;
        return this.number - ((DelayQueueTest) o).getNumber();
    }

    public static void main(String[] args) throws Exception {
        DelayQueue delayQueue = new DelayQueue();

        DelayQueueTest d1 = new DelayQueueTest(1000, 1);
        DelayQueueTest d2 = new DelayQueueTest(3000, 3);
        DelayQueueTest d3 = new DelayQueueTest(2000, 2);

        delayQueue.put(d1);
        delayQueue.put(d2);
        delayQueue.put(d3);

        System.out.println(delayQueue.take());
        System.out.println(delayQueue.take());
        System.out.println(delayQueue.take());
    }
}
