package com.coding.test;

/**
 * td1、td2 相互等待，都需要得到对方锁定的资源才能继续执行，从而死锁
 *
 * 如何避免死锁
 * 1)加锁顺序(线程按照一定的顺序加锁)
 * 2)加锁时限(线程尝试获取锁的时候加上一定的时限，超过时限则放弃对该锁的请求，并释放自己占有的锁)
 */
public class DeadLock implements Runnable {

    public int flag = 1;

    //静态对象是类的所有对象共享的
    private static Object o1 = new Object();
    private static Object o2 = new Object();

    public void run() {
        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("1");
                }
            }
        }

        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("0");
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock td1 = new DeadLock();
        DeadLock td2 = new DeadLock();
        td1.flag = 1;
        td2.flag = 0;
        //td1,td2 都处于可执行状态，但 JVM 线程调度先执行哪个线程是不确定的。
        //td2 的 run()可能在 td1 的 run()之前运行
        new Thread(td1).start();
        new Thread(td2).start();
    }
}
