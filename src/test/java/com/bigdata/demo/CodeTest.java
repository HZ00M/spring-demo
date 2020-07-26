package com.bigdata.demo;

import org.junit.Test;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class CodeTest {
    @Test
    public void test(){
        String[] strs = {"张三","李四","王五"};
        Comparator c = Collator.getInstance(Locale.CHINESE);
        Arrays.sort(strs,c);
        for (String str:strs){
            System.out.println(str);
        }
    }
}
