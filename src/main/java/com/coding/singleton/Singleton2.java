package com.coding.singleton;

//懒汉式-双重检测
public class Singleton2 {

    // 声明变量
    private static volatile Singleton2 singleton2 = null;

    // 私有化构造函数
    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (singleton2 == null) {
            synchronized (Singleton2.class) {
                if (singleton2 == null) {
                    singleton2 = new Singleton2();
                }
            }
        }
        return singleton2;
    }
}
