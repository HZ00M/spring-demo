package com.bigdata.demo.concurrent;

import com.bigdata.demo.service.TestService;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class CyclicBarrierTask implements Runnable {

    TestService testService;

    private CyclicBarrier cyclicBarrier ;

    public CyclicBarrierTask(CyclicBarrier cyclicBarrier,TestService testService){
        this.cyclicBarrier = cyclicBarrier;
        this.testService = testService;
    }

    @Override
    public void run() {
        try {
            System.out.println("到达集合点");
            cyclicBarrier.await();
            System.out.println("开始执行");
            testService.findAllBigdataWithRedis();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}
