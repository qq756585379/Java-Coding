package com.coding.integer;

import java.lang.reflect.Field;

public class Test {

    /**
     * a和b调用swap后值会交换吗
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a = 1, b = 2;
        System.out.println("before:a=" + a + ",b=" + b);
        // swap(a, b);
        // swap2(a, b);
        swap3(a, b);
        System.out.println("after:a=" + a + ",b=" + b);
    }

    /**
     * 值传递和引用传递，这边是值传递，不会改变原来a,b的值
     */
    private static void swap(Integer i1, Integer i2) {
        //上面形参是值传递
        Integer temp = i1;
        i1 = i2;
        i2 = temp;
    }

    /**
     * 值传递和引用传递，这边是引用传递
     */
    private static void swap2(Integer i1, Integer i2) throws NoSuchFieldException, IllegalAccessException {
        // 通过反射去修改Integer私有变量
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        int tmp = i1.intValue();//tmp指向i1内部的私有变量value
        // set(Object obj, Object value) 上面形参是对象传递，i1、i2是Object
        field.set(i1, i2.intValue());//Integer.valueOf(i2.intValue())，i2.intValue()=2
        //此时i1内部的私有变量value=2，tmp=2
        field.set(i2, tmp);//Integer.valueOf(tmp)
        //before:a=1,b=2
        //after:a=2,b=2
    }

    private static void swap3(Integer i1, Integer i2) throws NoSuchFieldException, IllegalAccessException {
        // 通过反射去修改Integer私有变量
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        Integer tmp = new Integer(i1.intValue());//tmp是个新对象

        // set(Object obj, Object value) 上面形参是对象传递，i1、i2是Object
        field.set(i1, i2.intValue());//Integer.valueOf(i2.intValue())
        //此时i1内部的私有变量value=2，tmp内部的value = 1
        field.set(i2, tmp);//Integer.valueOf(tmp)
        //before:a=1,b=2
        //after:a=2,b=1
    }
}
