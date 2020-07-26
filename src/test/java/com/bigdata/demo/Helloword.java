package com.bigdata.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class Helloword {
    //接口方式
    Thread thread1 = new Thread(new SayHello());
    //匿名类方式
    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("Hello world");
        }
    }) ;
    //lambda表达式
    Thread thread3 = new Thread(()-> System.out.println("Hello world"));

    @Test
    public void  test(){
        thread1.run();
        thread2.run();
        thread3.run();
    }

}
class SayHello implements Runnable{
    @Override
    public void run() {
        System.out.println("Hello world");
    }
}
