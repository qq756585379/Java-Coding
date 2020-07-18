package com.coding.concurrent02;

import lombok.Data;

@Data
public class MyTask implements Runnable {

    private int taskId;
    private String taskName;

    MyTask(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        try {
            System.out.println("run taskId =" + this.taskId);
            Thread.sleep(5 * 1000);
            System.out.println("end taskId =" + this.taskId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return Integer.toString(this.taskId);
    }
}
