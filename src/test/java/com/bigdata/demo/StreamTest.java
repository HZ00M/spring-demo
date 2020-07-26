package com.bigdata.demo;

import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    @Test
    public void  test(){
        List<Item> list = Stream.generate(Random::new).limit(10).map(a -> {
            return new Item(a.nextInt(10));
        }).collect(Collectors.toList());

        list.stream().forEach(System.out::println);
        System.out.println();
        //去重
//        list.stream().distinct().forEach(System.out::println);
//        //截断
//        list.stream().limit(3).forEach(System.out::println);
//        //筛选
//        list.stream().filter((a)->a.getNo()>5).forEach(System.out::println);
//        //跳过
//        list.stream().skip(4).forEach(System.out::println);
//        //映射
//        list.stream().map(Item::getNo).forEach(System.out::println);
//        //流的扁平化 ，返回name 出现的所有字符
//        list.stream().map((a)->{
//            return a.getName().split("");
//        }).flatMap(Arrays::stream).distinct().forEach(System.out::println);
//        //匹配与查找
//        list.stream().map(Item::getNo).anyMatch(i->i>5);
//        list.stream().map(Item::getNo).anyMatch(i->i>5);
//        list.stream().map(Item::getNo).noneMatch(i->i<=5);
//        list.stream().map(Item::getNo).filter(i->i>5).findAny();
//        list.stream().map(Item::getNo).filter(i->i>5).findFirst();
//        list.stream().map(Item::getNo).filter(i->i>5).findFirst();
//        //归约
//        System.out.println(list.stream().map(Item::getNo).reduce(0,Integer::sum));
//        //汇总
//        System.out.println(list.stream().collect(Collectors.summingInt(Item::getNo)));
//          //连接字符串
//        System.out.println(list.stream().map(Item::getName).collect(Collectors.joining()));
//        System.out.println(list.stream().map(Item::getName).collect(Collectors.reducing((a,b)->{return a + b;})));
//          //分组
//        Map<String, List<Item>> collect = list.stream().collect(Collectors.groupingBy(Item::getName));
//        System.out.println(collect);
//        //多级分组 奇数为一组，偶数为一组
//        Map<String, Map<String, List<Item>>> collect = list.stream().collect(Collectors.groupingBy(a -> {
//            if (a.getNo() % 2 == 0) {
//                return "偶数组";
//            } else return "奇数组";
//        }, Collectors.groupingBy(Item::getName)));
//        System.out.println(collect);
//        //按子组收集数据
//        list.stream().collect(Collectors.groupingBy(a -> {
//            if (a.getNo() % 2 == 0) {
//                return "偶数组";
//            } else return "奇数组";
//        }, Collectors.groupingBy(Item::getName)));
        //分区
        Map<Boolean, List<Item>> collect = list.stream().collect(Collectors.partitioningBy(a -> {
            if (a.getNo() % 2 == 0) {
                return true;
            } else return false;
        }));
        System.out.println(collect);
    }

}
@Data
class Item{
    private int no;
    private String name;

    Item(int seed){
        this.no = seed;
        this.name =  "name" + seed;
    }

    @Override
    public String toString() {
        return "item " + no +" name "+ name;
    }

    @Override
    public boolean equals(Object obj) {
        return no==((Item)obj).no&&name.equals(((Item)obj).name);
    }

    @Override
    public int hashCode() {
        return String.valueOf(no).hashCode();
    }
}
