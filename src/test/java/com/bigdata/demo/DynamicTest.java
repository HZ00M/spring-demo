package com.bigdata.demo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;

import java.lang.reflect.Field;

public class DynamicTest {
    public static void main(String[] args) throws ClassNotFoundException {
        String[] str = new String[10];
        Class<?> aClass = Class.forName("java.lang.String[]");
        Class<?> aClass1 = Class.forName("[J");
    }

    @Test
    public void test(){
        Exception exception = new IllegalArgumentException();
        exception.fillInStackTrace();
        StackTraceElement[] eles = new Throwable().getStackTrace();
        for (StackTraceElement ele:eles){
            System.err.println(ele.toString());
        }
    }

    @Test
    public  void reflect()throws Exception{
        A a= new A();
        Field[] declaredFields = a.getClass().getDeclaredFields();
        for (Field field :declaredFields){
            System.out.println(field.getName());
        }
        Class<?> declaringClass = declaredFields[0].getDeclaringClass();
        System.out.println(declaringClass.getName());
    }
}
@Setter
@Getter
@NoArgsConstructor
class  A{
    private  int a;
}
