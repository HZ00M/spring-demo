package com.bigdata.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperTest {
    @Value("${zk.url}")
    String zkUrl;

    @Test
    public void zkTest() throws Exception{
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(zkUrl,new RetryOneTime(1000));
        zkClient.start();//启动zookeeper连接
        //获取节点的值
        byte[] bytes = zkClient.getData().forPath("/config");
        System.out.println("节点的值是"+new String(bytes));

        //获取节点下的子节点
        List<String> strings = zkClient.getChildren().forPath("/config");
        strings.forEach(s->{
            System.out.println(s);
        });
    }
}
