package com.coding.thread;

public class Producer implements Runnable{

    private ProducerTest res;

    Producer(ProducerTest res) {
        this.res = res;
    }

    @Override
    public void run() {
        while (true) {
            res.set("商品");
        }
    }
}
