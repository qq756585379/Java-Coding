package com.coding.concurrent01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class UseFuture implements Callable<String> {

    private String para;

    private UseFuture(String para) {
        this.para = para;
    }

    /**
     * 这里是真实的业务逻辑，其执行可能很慢
     */
    @Override
    public String call() throws Exception {
        //模拟执行耗时
        Thread.sleep(5000);
        return this.para + "处理完成";
    }

    //主控制函数
    public static void main(String[] args) throws Exception {
        //创建一个固定线程的线程池
        ExecutorService executor = Executors.newFixedThreadPool(1);

        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Future f = executor.submit(new UseFuture("i:" + i));
            futures.add(f);
        }

        //submit和execute的区别： 第一点是submit可以传入实现Callable接口的实例对象， 第二点是submit方法有返回值
        // Future f1 = executor.submit(future);        //单独启动一个线程去执行的
        // Future f2 = executor.submit(future2);

        // executor.execute(future);
        // executor.execute(future2);
        System.out.println("提交任务完毕");

        // try {
        //     //这里可以做额外的数据操作，也就是主程序执行其他业务逻辑
        //     System.out.println("处理实际的业务逻辑...");
        //     Thread.sleep(1000);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        for (int i = 0; i < 3; i++) {
            Future future = futures.get(i);
            //调用获取数据方法,如果call()方法没有执行完成,则依然会进行等待
            System.out.println("数据：" + future.get());//get方法会阻塞住直到任务完成

        }

        System.out.println("********************");
        executor.shutdown();
    }
}
