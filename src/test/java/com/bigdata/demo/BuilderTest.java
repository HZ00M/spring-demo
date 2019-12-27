package com.bigdata.demo;

import lombok.*;
import org.junit.Test;


public class BuilderTest {


    @Test
    public void test(){
        Person person = Person.builder().age(1).name("hzoom").build();
        System.out.println(person.getAge());
        System.out.println(person.getName());
        Person hello = person.toBuilder().name("hello").build();
        System.out.println(hello.getAge());
        System.out.println(hello.getName());
        Person all = new Person("张三",2);
        System.out.println(all.getAge());
        System.out.println(all.getName());
    }
}

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
class Person{
    private String name;
    private int age;
}