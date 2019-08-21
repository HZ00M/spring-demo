package com.bigdata.demo.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//监听zookeeper节点变化
@Component
public class ZKWatch {
    @Value("${zk.url}")
    private String zkUrl;

    @Autowired
    ZKConfig zkConfig;

    //启动一个监听机制
    @PostConstruct
    public void initWatch(){
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(zkUrl,new RetryOneTime(1000));
        zkClient.start();

        try{
            //监听节点及子节点的CRUD
            TreeCache treeCache = new TreeCache(zkClient,"/config");
            treeCache.start();
            treeCache.getListenable().addListener(new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework zkClient, TreeCacheEvent event) throws Exception {
                    switch (event.getType()){
                        case NODE_UPDATED:
                            System.out.println("数据发送变化");
                            if(event.getData().getPath().equals("/config/config1")){
                                zkConfig.setConfig1(new String(event.getData().getData()));
                            }
                            if(event.getData().getPath().equals("/config/config2")){
                                zkConfig.setConfig1(new String(event.getData().getData()));
                            }
                            break;
                            default:
                                break;
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
