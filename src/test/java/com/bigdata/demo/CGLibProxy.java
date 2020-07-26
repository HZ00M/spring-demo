package com.bigdata.demo;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxy implements MethodInterceptor{
    private Enhancer enhancer = new Enhancer(); // cglib 中的Enhancer 对象
    public Object getProxy(Class clazz) {
        enhancer.setSuperclass(clazz); // 指定生成的代~类的父类
        enhancer.setCallback(this); // 放置Callback 对象
        return enhancer.create(); // 通过字节码技术动态创建子类实例
    }
        // 实现Methodinterceptor接口的intercept()方法
        public Object intercept( Object obj , Method method, Object[] args ,
                MethodProxy proxy) throws Throwable {
            System.out.println("前直处理");
            Object result = proxy.invokeSuper(obj, args); // 调用父类中的方法
            System.out.println(" 后直处理");
            return result;
        }
}
