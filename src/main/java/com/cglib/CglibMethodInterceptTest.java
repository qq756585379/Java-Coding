package com.cglib;

import com.pojo.Student;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

public class CglibMethodInterceptTest {

    public static void main(String[] args) {
        cglibTest1();
//        cglibTest2();
    }

    private static void cglibTest1() {
        //创建一个Enhancer对象
        Enhancer enchaner = new Enhancer();
        //设置被代理的类
        enchaner.setSuperclass(Student.class);
        //创建一个回调接口
        Callback interceptor = new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.err.println("原方法名是 ： " + method.getName());
                System.err.println("原方法声明的类为 " + method.getDeclaringClass());
                System.err.println("我是 " + (String) proxy.invokeSuper(obj, args));
                System.err.println("我调用结束了");
                return null;
            }
        };
        enchaner.setCallback(interceptor);
        Student student = (Student) enchaner.create();
        System.err.println("**********************************");
        System.err.println(student.getName());
        System.err.println("**********************************");
    }

    private static void cglibTest2() {
        //创建一个Enhancer对象
        Enhancer enchaner = new Enhancer();
        //设置被代理的类
        enchaner.setSuperclass(Student.class);
        Callback interceptor = new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.err.println("原方法名是 ： " + method.getName());
                System.err.println("原方法声明的类为 " + method.getDeclaringClass());
                System.err.println("我是 " + (String) proxy.invokeSuper(obj, args));
                System.err.println("我调用结束了");
                return proxy.invokeSuper(obj, args);
            }
        };
        CallbackFilter callbackFilter = new CallbackFilter() {
            @Override
            public int accept(Method method) {
                int flag = 0;
                if ("getName".equals(method.getName())) {
                    System.err.println("我将此方法过滤掉了，不对该方法进行拦截");
                    return 1;
                }
                return 0;
            }
        };
        //NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
        Callback[] callbacks = new Callback[]{interceptor, NoOp.INSTANCE};
        enchaner.setCallbackFilter(callbackFilter);
        enchaner.setCallbacks(callbacks);
        Student student = (Student) enchaner.create();
        System.err.println("**********************************");
        System.err.println(student.getName());
        System.err.println("**********************************");
        System.err.println(student.getRename());
        System.err.println("**********************************");
    }
}
