package com.bigdata.demo;

import java.util.concurrent.CompletableFuture;

public class FutureTest {
    public static void main(String[] args) {
        CompletableFuture<Double> futurePrice = getPriceAsync();
        //do anything you want, 当前线程不被阻塞
        System.out.println(111);
        //线程任务完成的话，执行回调函数，不阻塞后续操作
        futurePrice.whenComplete((aDouble, throwable) -> {
            System.out.println(aDouble);
            //do something else
        });
        System.out.println(222);
    }

    static CompletableFuture<Double> getPriceAsync() {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            futurePrice.complete(23.55);
        }).start();
        return futurePrice;
    }
}
