package com.coding.proxy;

import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

public class MyInvationHandler implements InvocationHandler {

    private Object target;

    public MyInvationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        System.out.println("进来了");
        Object obj = method.invoke(target, args);
        System.out.println("出去了");
        return obj;
    }
}
