package com.thread.study.futuretask.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class DrinkTea2 {
    static class T1 implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println(Thread.currentThread().getName() + "烧水");
                Thread.sleep(500L);
                System.out.println(Thread.currentThread().getName() + "水开了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    static class T2 implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println(Thread.currentThread().getName() + "洗茶具");
                Thread.sleep(500L);
                System.out.println(Thread.currentThread().getName() + "洗完了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public static void getResult(boolean b1, boolean b2) {
        if (b1 && b2) {
            System.out.println("喝茶");
        } else if (!b1) {
            System.out.println("烧水失败");
        } else {
            System.out.println("洗杯子失败");
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Boolean> t1 = new T1();
        FutureTask<Boolean> task1 = new FutureTask<>(t1);
        Thread thread1 = new Thread(task1,"t1");
        Callable<Boolean> t2 = new T2();
        FutureTask<Boolean> task2 = new FutureTask<>(t2);
        Thread thread2 = new Thread(task2,"t2");

        thread1.start();
        thread2.start();
        Boolean aBoolean1 = task1.get();
        Boolean aBoolean2 = task2.get();
        getResult(aBoolean1,aBoolean2);

    }
}
