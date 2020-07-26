package com.bigdata.decorate;

public class FlyFeature implements Feature{
    @Override
    public void load() {
        System.out.println("飞行能力");
    }
}
