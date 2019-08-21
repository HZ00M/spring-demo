package com.bigdata.demo;

import com.bigdata.demo.concurrent.CyclicBarrierTask;
import com.bigdata.demo.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CyclicBarrierTest {

    @Autowired
    TestService testService;

    @Test
    public void Test(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        Executor executor = Executors.newFixedThreadPool(10);
        for (int i = 0 ; i<10;i++){
            executor.execute(new CyclicBarrierTask(cyclicBarrier,testService));
        }
    }
}
