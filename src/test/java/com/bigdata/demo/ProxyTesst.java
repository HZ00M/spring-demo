package com.bigdata.demo;

import com.bigdata.demo.proxy.*;
import org.junit.Test;

public class ProxyTesst {
    @Test
    public static void main(String[] args) {
        TargetInterface t = new TimeTarget();
        ProxyInvocationHandler handler = new TimeHandler(t);
        TargetInterface proxyTarget = (TargetInterface)Proxy.instanceProxy(handler,t);
        System.out.println(proxyTarget);
        proxyTarget.targetMethod();
    }
}
