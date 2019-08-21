package com.bigdata.demo;

import java.util.Random;

public class FlyTest {
    public interface Flyable {
        void fly();
    }
    public class Bird implements Flyable {

        @Override
        public void fly() {
            System.out.println("Bird is flying...");
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public class Bird2 extends Bird {

        @Override
        public void fly() {
            long start = System.currentTimeMillis();

            super.fly();

            long end = System.currentTimeMillis();
            System.out.println("Fly time = " + (end - start));
        }
    }
}
