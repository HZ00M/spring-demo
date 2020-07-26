package com.bigdata.demo;

import org.junit.Test;

public class EnumTest {
    @Test
    public void test(){
        Season season=Season.Spring;
        Season season1 = null;
        switch (season1.ordinal()){
            case 0:
                System.out.println(season);
                break;
            default:
                System.out.println("not match");
                break;
        }
    }
}

enum Season{
    Spring,Sumnner,Autumn,Winter;
}