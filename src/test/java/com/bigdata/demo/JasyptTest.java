package com.bigdata.demo;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class JasyptTest {
    @Autowired
    StringEncryptor encryptor;

    @Test
    public void getPass(){
        String url = encryptor.encrypt("jdbc:mysql://192.168.68.4:13306/bigdata?useUnicode=true&characterEncoding=UTF-8");
        String name = encryptor.encrypt("bigdata");
        String password = encryptor.encrypt("P@ssw0rd520!");
        System.out.println(url+"----------------");
        System.out.println(name+"----------------");
        System.out.println(password+"----------------");
//        Assert.assertTrue(name.length() > 0);
//        Assert.assertTrue(password.length() > 0);
    }
}
