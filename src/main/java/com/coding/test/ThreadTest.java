package com.coding.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 线程通信的方式:
 * 1.共享变量
 * 2.wait/notify 机制
 */
public class ThreadTest {

    //共享的变量
    private boolean hasDataToProcess = false;

    public boolean isHasDataToProcess() {
        return hasDataToProcess;
    }

    public void setHasDataToProcess(boolean hasDataToProcess) {
        this.hasDataToProcess = hasDataToProcess;
    }

    public static void main(String[] args) {
        final ThreadTest my = new ThreadTest();

        //线程1设置 hasDataToProcess值为true
        final Thread t1 = new Thread(new Runnable() {
            public void run() {
                my.setHasDataToProcess(true);
            }
        });

        //线程2 取这个值 hasDataToProcess
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    t1.join();//当前线程挂起，等t1线程完成
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("t1 改变以后的值:" + my.isHasDataToProcess());
            }
        });

        t1.start();
        t2.start();

        // 写一个 ArrayList 的动态代理类
        final List<String> list = new ArrayList<>();
        List<String> proxyInstance = (List<String>) Proxy.newProxyInstance(list.getClass().getClassLoader(),
                list.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("**********");
                        return method.invoke(list, args);
                    }
                });

        proxyInstance.add("你好");
        System.out.println(list);
    }
}
