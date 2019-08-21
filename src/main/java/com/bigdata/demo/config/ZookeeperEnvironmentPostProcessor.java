package com.bigdata.demo.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//增强扩展spring功能
//让spring支持从zookeeper中读取配置信息到Env
public class ZookeeperEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("----------增强spring代码执行-----------");
        String zkUrl = environment.getProperty("zk.url");
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(zkUrl,new RetryOneTime(1000));
        zkClient.start();//启动Zookeeper连接
        try {
            Stat stat = zkClient.checkExists().forPath("/config");
            if(stat==null){
                //创建临时节点
                zkClient.create().forPath("/config");
                //创建永久节点，带初始值
                zkClient.create().forPath("/config/config1","default1".getBytes());
                zkClient.create().forPath("/config/config2","default2".getBytes());
            }
            Map<String,Object> configMap = new HashMap<>();
            List<String> configNodes = zkClient.getChildren().forPath("/config");
            configNodes.forEach(s->{
                byte[] obj = new byte[0];
                try {
                    obj = zkClient.getData().forPath("/config/"+s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                configMap.put(s,new String(obj));
            });
            MapPropertySource source = new MapPropertySource("zkPropertySource",configMap);
            environment.getPropertySources().addLast(source);
        }catch (Exception   e){

        }
    }
}
