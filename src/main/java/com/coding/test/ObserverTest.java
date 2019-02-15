package com.coding.test;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 观察者模式
 */
interface Observer {
    void update();
}

interface Subject {
    /*增加观察者*/
    void add(Observer observer);

    /*删除观察者*/
    void del(Observer observer);

    /*通知所有的观察者*/
    void notifyObservers();

    /*自身的操作*/
    void operation();
}

class Observer1 implements Observer {
    @Override
    public void update() {
        System.out.println("observer1 has received!");
    }
}

class Observer2 implements Observer {
    @Override
    public void update() {
        System.out.println("observer2 has received!");
    }
}

abstract class AbstractSubject implements Subject {
    private Vector<Observer> vector = new Vector<Observer>();

    /*增加观察者*/
    public void add(Observer observer) {
        vector.add(observer);
    }

    /*删除观察者*/
    public void del(Observer observer) {
        vector.remove(observer);
    }

    /*通知所有的观察者*/
    public void notifyObservers() {
        Enumeration<Observer> enumo = vector.elements();
        while (enumo.hasMoreElements()) {
            enumo.nextElement().update();
        }
    }
}

class MySubject extends AbstractSubject {
    @Override
    public void operation() {
        System.out.println("update self!");
        notifyObservers();
    }
}

class ObserverTest {
    public static void main(String[] args) {
        Subject sub = new MySubject();
        sub.add(new Observer1());
        sub.add(new Observer2());
        sub.operation();
    }
}