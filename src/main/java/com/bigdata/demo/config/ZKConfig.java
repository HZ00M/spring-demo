package com.bigdata.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//@Component
//@ConfigurationProperties(prefix = "config")
public class ZKConfig {
    @Value("${config1}")
    private  String config1 ;
    @Value("${config2}")
    private  String config2 ;
    public String getConfig1(){
        return  config1;
    }
    public String getConfig2(){
        return  config2;
    }

    public void setConfig1(String config1) {
        this.config1 = config1;
    }

    public void setConfig2(String config2) {
        this.config2 = config2;
    }
}
