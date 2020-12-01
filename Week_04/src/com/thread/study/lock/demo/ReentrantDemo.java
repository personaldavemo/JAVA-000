package com.thread.study.lock.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantDemo {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        /**
         * 可重入实验（同一个线程，锁几次释放几次）
         */
        lock.lock();
        System.out.println("第一次加锁");
        lock.lock();
        System.out.println("第二次加锁");
        lock.unlock();
        lock.unlock();
    }
}
