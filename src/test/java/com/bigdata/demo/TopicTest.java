package com.bigdata.demo;

import com.bigdata.demo.config.TopicAutoConfig;
import com.bigdata.demo.proxy.Proxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicTest {
    @Autowired
    TopicAutoConfig topic;
    @Test
    public void test(){
        Proxy proxy = new Proxy();
    }
}

