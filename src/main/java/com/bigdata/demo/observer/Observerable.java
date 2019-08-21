package com.bigdata.demo.observer;

public interface Observerable {
    void register(Observer o);
    void remove(Observer o);
    void notifyObserver();
}
