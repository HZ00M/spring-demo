package com.bigdata.demo.observer;

import java.util.ArrayList;

public class WechatServer implements Observerable{
    private ArrayList<Observer> observers;
    private String message;
    public WechatServer(){
        observers = new ArrayList<>();
    }
    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void remove(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver() {
        for (int i=0;i<observers.size();i++){
            Observer observer = observers.get(i);
            observer.update(message);
        }
    }

    public void setInfo(String message){
        this.message = message;
        System.out.println("微信服务更新消息"+message);
        notifyObserver();
    }
}
