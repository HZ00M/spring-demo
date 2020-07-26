package com.bigdata.demo;

import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
public class CGLibTest {
    public String method(String str) {
        System.out.println(str);
        return " CGLibTest . method(): " + str;
    }
        public static void main (String[]args){
            CGLibProxy proxy = new CGLibProxy();
            CGLibTest proxyimp = (CGLibTest) proxy.getProxy(CGLibTest.class);
            String result = proxyimp.method("test ");
            System.out.println(result); 
        }
    } 