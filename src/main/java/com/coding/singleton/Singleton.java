package com.coding.singleton;

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
