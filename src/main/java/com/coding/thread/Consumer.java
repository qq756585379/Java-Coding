package com.coding.thread;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.concurrent.*;

public class Consumer implements Runnable {

    private ProducerTest res;

    Consumer(ProducerTest res) {
        this.res = res;
    }

    @Override
    public void run() {
        while (true) {
            res.out();
        }
    }
}
