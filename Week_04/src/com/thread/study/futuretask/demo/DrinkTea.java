package com.thread.study.futuretask.demo;

public class DrinkTea {
    static class T1 extends Thread {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "烧水");
                Thread.sleep(500L);
                System.out.println(Thread.currentThread().getName() + "水开了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class T2 extends Thread {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "洗茶具");
                Thread.sleep(500L);
                System.out.println(Thread.currentThread().getName() + "洗完了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
     Thread t1 = new T1();
     Thread t2 = new T2();

        t1.start();
        t2.start();
        //等待T2执行完继续执行T1
        t1.join();
        //等待所有线程执行完执行主线程
        t2.join();
        System.out.println(Thread.currentThread().getName() + "泡茶");
    }
}
