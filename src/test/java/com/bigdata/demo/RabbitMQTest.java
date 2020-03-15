package com.bigdata.demo;

import com.bigdata.demo.component.MsgProducer;
import com.bigdata.demo.config.RabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

//    @Autowired
    MsgProducer producer;

    @Test
    public void Test(){
        for(int i =0;i<20;i++){
            producer.sendMsgTopic("ZImingHuang"+i, "lay.b.c");
        }

    }
}
