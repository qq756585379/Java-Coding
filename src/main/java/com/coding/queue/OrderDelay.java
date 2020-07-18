package com.coding.queue;

import lombok.Data;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
public class OrderDelay implements Delayed {

    private int orderId;

    private Date orderTime;

    private static final int expireTime = 15000;//15秒

    @Override
    public long getDelay(TimeUnit unit) {
        return orderTime.getTime() + expireTime - new Date().getTime();
    }

    @Override
    public int compareTo(Delayed o) {
        return this.orderTime.getTime() - ((OrderDelay) o).orderTime.getTime() > 0 ? 1 : -1;
    }
}