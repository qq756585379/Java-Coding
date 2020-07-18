package com.coding.observer;

import java.util.Enumeration;
import java.util.Vector;

public class AbstractSubject implements Subject {

    private Vector<Observer> vector = new Vector<>();

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

    @Override
    public void operation() {

    }
}
