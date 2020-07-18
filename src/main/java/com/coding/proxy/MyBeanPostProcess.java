package com.coding.proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcess implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return Proxy.newProxyInstance(MyBeanPostProcess.class.getClassLoader(), bean.getClass().getInterfaces(), new MyInvationHandler(bean));
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("beanName: " + beanName);
        return bean;
    }
}
