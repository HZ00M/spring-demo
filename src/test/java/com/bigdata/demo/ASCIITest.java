package com.bigdata.demo;

import org.junit.Test;

public class ASCIITest {
    @Test
    public void test() {
        String s = "hello";
        char[] chars = s.toCharArray();
        for (char c : chars) {
            System.out.println(Integer.valueOf(c));
            System.out.println(Integer.toBinaryString(c));
        }

    }

    @Test
    public void test1() {
        int i = 1 << 0;
        int j = i << 4;
        int OP_READ = 1;
        int OP_WRITE = 4;
        int OP_CONNECT = 8;
        int OP_ACCEPT = 16;
        System.out.println(i | j);
        System.out.println(i & j);

        int op = 8;
        op &= OP_CONNECT ;
        System.out.println(op);
        int op2 = 19;
        op &= ~OP_CONNECT ;
        System.out.println(~OP_CONNECT);
    }
}
