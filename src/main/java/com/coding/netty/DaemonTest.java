package com.coding.netty;

import sun.misc.Signal;

import java.util.concurrent.TimeUnit;

public class DaemonTest {

    public static void main(String[] args) throws InterruptedException {
        shutdownHook();
    }

    private static void shutdownHook() throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new java.lang.Thread(() -> {
            System.out.println("ShutdownHook execute start...");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("ShutdownHook execute end...");
        }, ""));
        TimeUnit.SECONDS.sleep(7);
        System.exit(0);
    }

    private static void daemonThread() throws InterruptedException {
        long startTime = System.nanoTime();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.DAYS.sleep(Long.MAX_VALUE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Daemon-T");
        // 所有非守护线程全部执行完，进程才会退出
        t.setDaemon(true);
        t.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("系统退出，程序执行" + (System.nanoTime() - startTime) / 1000 / 1000 / 1000 + "s");
    }

    private static void signalExit() {
        Signal signal = new Signal("INT");
        Signal.handle(signal, (s) -> {
            System.out.println("Signal handle start...");
            try {
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
