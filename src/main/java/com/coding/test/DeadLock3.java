package com.coding.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * td1、td2 相互等待，都需要得到对方锁定的资源才能继续执行，从而死锁
 * <p>
 * 如何避免死锁
 * 1)加锁顺序(线程按照一定的顺序加锁)
 * 2)加锁时限(线程尝试获取锁的时候加上一定的时限，超过时限则放弃对该锁的请求，并释放自己占有的锁)
 */
public class DeadLock3 {

    //静态对象是类的所有对象共享的
    private static Object o1 = new Object();
    private static Object o2 = new Object();

    public void money(int flag) {
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("当前的线程是" + Thread.currentThread().getName() + " " + "flag 的值" + "0");
                }
            }
        }
    }

    private static boolean tryLock(Lock lock) {
        try {
            String tName = Thread.currentThread().getName();
            //获取不到锁，就等 5 秒，如果 5 秒后还是获取不到就返回 false
            if (lock.tryLock(5000, TimeUnit.MILLISECONDS)) {
                System.out.println(tName + "获取到锁!");
                return true;
            } else {
                System.out.println(tName + "获取不到锁!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        final Lock lock = new ReentrantLock();
        final DeadLock3 td1 = new DeadLock3();
        final DeadLock3 td2 = new DeadLock3();

        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!DeadLock3.tryLock(lock)) return;
                try {
                    td1.money(1);
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "出错了!!!");
                } finally {
                    System.out.println("当前的线程是" + Thread.currentThread().getName() + "释放锁!!");
                    lock.unlock();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String tName = Thread.currentThread().getName();
                if (!DeadLock3.tryLock(lock)) return;
                try {
                    td2.money(0);
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "出错了!!!");
                } finally {
                    System.out.println("当前的线程是" + Thread.currentThread().getName() + "释放锁!!");
                    lock.unlock();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
