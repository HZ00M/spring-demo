package com.bigdata.demo.proxy;

public class MyTarget implements TargetInterface {

    public MyTarget(){};
    @Override
    public void targetMethod() {
        System.out.println("代理方法");
    }


}
