package com.coding.test;

// 饿汉式:
public class Singleton {

    // 直接创建对象
    private static Singleton instance = new Singleton();

    // 私有化构造函数
    private Singleton() {
    }

    // 返回对象实例
    public static Singleton getInstance() {
        return instance;
    }
}

// 懒汉式:
class Singleton2 {

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
