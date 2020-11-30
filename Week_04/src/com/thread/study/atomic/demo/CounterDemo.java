package com.thread.study.atomic.demo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("all")
public class CounterDemo {
//    volatile int i = 0;
    //单线程
//    public synchronized void add(){
//        i++;
//    }

//    Lock lock = new ReentrantLock();
//    public void add(){
//        lock.lock();
//        i++;
//        lock.unlock();
//    }
    //原子类
AtomicInteger atomicInteger = new AtomicInteger(0);
public void add(){
    atomicInteger.incrementAndGet();
}
    public static void main(String[] args) throws InterruptedException {
        final CounterDemo demo = new CounterDemo();
        for (int i = 0; i < 5 ; i++) {
            new Thread(() ->{
                for (int j = 0; j < 10000 ; j++) {
                    demo.add();
                }
                System.out.println(Thread.currentThread().getName() + "-add");
            }).start();
        }
        Thread.sleep(5000L);
        System.out.println(demo.atomicInteger.get());
    }
}
