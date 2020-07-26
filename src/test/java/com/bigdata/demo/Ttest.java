package com.bigdata.demo;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ttest {
    @Test
    public void test(){
        Foo<Integer> foo = new Foo(String.class);
        System.out.println(foo);
    }

    public static <T extends Staff & Passenger> void discount(T t){
        if (t.getSalary()<2500&&t.isStanding()){
            System.out.println("打折");
        }else {
            System.out.println("不打折");
        }
    }

    @Test
    public void test2(){
        Me me = new Me();
        He he = new He();
        discount(me);
    }

    public static <T> T[] toArray(List<T> list){
        T[] t = (T[]) new Object[list.size()];
        for (int i=0,n=list.size();i<n;i++){
            t[i] = list.get(i);
        }
        return t;
    }
    public static <T> T[] toArray(List<T> list,Class<T> tClass){
        T[] t = (T[]) Array.newInstance(tClass,list.size());
        for (int i=0,n=list.size();i<n;i++){
            t[i] = list.get(i);
        }
        return t;
    }

    @Test
    public void test3(){
        List<String> list = Arrays.asList("A","B");
        for (String str:toArray(list)){
            System.out.println(str);
        }
    }
    @Test
    public void test4(){
        List<String> list = Arrays.asList("A","B");
        for (String str:toArray(list,String.class)){
            System.out.println(str);
        }
    }
}
interface Staff{
    public int getSalary();
}
interface Passenger{
    public boolean isStanding();
}

class He implements Passenger{

    @Override
    public boolean isStanding() {
        return true;
    }
}

class Me implements Staff,Passenger{

    @Override
    public int getSalary() {
        return 2000;
    }

    @Override
    public boolean isStanding() {
        return true;
    }
}
class Foo<T>{
    private T t ;
    private T[] array ;
    private List<T> list = new ArrayList<T>();
    public Foo(Class type){

        try {
            t = (T) type.newInstance();
            array = (T[]) Array.newInstance(type,5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return t.getClass().getTypeName()+array.length;
    }
}
