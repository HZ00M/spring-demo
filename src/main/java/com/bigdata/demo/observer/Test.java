package com.bigdata.demo.observer;

public class Test {
    public static void main(String[] args) {
        WechatServer server = new WechatServer();
        Observer zhangsan = new User("zhangsan");
        Observer lisi = new User("lisi");
        server.register(zhangsan);
        server.register(lisi);
        server.setInfo("观察者测试");
    }
}
