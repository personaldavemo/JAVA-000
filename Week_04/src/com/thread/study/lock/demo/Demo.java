package com.thread.study.lock.demo;
@SuppressWarnings("all")
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        int i = 10;
        new Thread(() -> {
            //栈中的属性？
            int a = i + 1;
            System.out.println(a);
        }).start();
        Thread.sleep(2000L);
    }
}
