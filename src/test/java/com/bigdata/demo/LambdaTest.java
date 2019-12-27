package com.bigdata.demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaTest {
    @Test
    public void testFnction(){
        Function<Integer,String> fun = (x)->String.valueOf(x*2);
        System.out.println(fun.apply(100));
    }
    @Test
    public void testConsumer(){
        Consumer<Integer> consumer = (x)-> System.out.println(x);
        consumer.accept(100);
    }

    @Test
    public void testPredicate(){
        Predicate<Integer> predicate = (x)->x==100;
        System.out.println(predicate.test(100));
        System.out.println(predicate.test(10));
    }
    public   void eval(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer n : list) {
            if (predicate.test(n)) {
                System.out.print(n + " ");
            }
        }

    }
    @Test
    public  void testPredicate2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println("输出所有数据:");

        // 传递参数 n
        eval(list, n -> true);

        System.out.println("\n输出所有偶数:");
        eval(list, n -> n % 2 == 0);

        System.out.println("\n输出大于 3 的所有数字:");
        eval(list, n -> n > 3);
    }

    @Test
    public void testSupplier(){
        String name ="黄梓铭";
        Supplier<String> supplier = ()->name.length()+"";
        System.out.println(supplier.get());
    }
}
