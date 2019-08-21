package com.bigdata.demo.concurrent;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTask implements Runnable{

    private static CountDownLatch countDownLatch = new CountDownLatch(100);

    private List<Runnable> tasks ;

    public CountDownLatchTask(CountDownLatch countDownLatch, List<Runnable> tasks) {
        this.countDownLatch = countDownLatch;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        try {
            tasks.forEach(t->{
                countDownLatch.countDown();
                t.run();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
