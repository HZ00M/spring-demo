package com.bigdata.demo.proxy;

import java.lang.reflect.Method;

public interface ProxyInvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args);
}
