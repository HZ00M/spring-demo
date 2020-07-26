package com.hzoom.demo.activeObject;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {
    public static void main(String[] args) throws Exception{
        SampleActiveObject sao = AbstractObjectProxy.newInstance(SampleActiveObject.class,new SampleActiveObjectImpl(), Executors.newCachedThreadPool());
        Future<String> f = sao.getA();
        Thread.sleep(50);
        System.out.println(f.get());
    }
}
