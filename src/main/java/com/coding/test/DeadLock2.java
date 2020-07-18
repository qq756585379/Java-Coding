package com.coding.test;

/**
 * td1、td2 相互等待，都需要得到对方锁定的资源才能继续执行，从而死锁
 * <p>
 * 如何避免死锁
 * 1)加锁顺序(线程按照一定的顺序加锁)
 * 2)加锁时限(线程尝试获取锁的时候加上一定的时限，超过时限则放弃对该锁的请求，并释放自己占有的锁)
 */
public class DeadLock2 {

    public int flag = 1;

    //静态对象是类的所有对象共享的
    private static Object o1 = new Object();
    private static Object o2 = new Object();

    public void money(int flag) {
        this.flag = flag;
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("当前的线程是" + Thread.currentThread().getName() + " " + "flag 的值" + "1");
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
                    System.out.println("当前的线程是" + Thread.currentThread().getName() + " " + "flag 的值" + "0");
                }
            }
        }
    }

    public static void main(String[] args) {
        final DeadLock2 td1 = new DeadLock2();
        final DeadLock2 td2 = new DeadLock2();
        td1.flag = 1;
        td2.flag = 0;

        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                td1.flag = 1;
                td1.money(1);
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //让t2等待t1执行完,核心代码，让t1执行完后t2才会执行
                    t1.join();//释放自己的锁
                } catch (Exception e) {
                    e.printStackTrace();
                }
                td2.flag = 0;
                td1.money(0);
            }
        });
        t2.start();
    }
}
