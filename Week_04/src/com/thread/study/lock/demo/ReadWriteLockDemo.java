package com.thread.study.lock.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@SuppressWarnings("all")
public class ReadWriteLockDemo {
    volatile long i = 0;
//    Lock lock = new ReentrantLock();
ReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * reentrantLoxk同时加锁单线程
     */
    public void read(){
        lock.readLock().lock();
        long a = i;
        lock.readLock().unlock();
    }

    /**
     * 读写锁，读共享，写互斥
     * 关联锁
     */
    public void write(){
        lock.writeLock().lock();
        i++;
        lock.writeLock().unlock();
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 30 ; i++) {
            int n = i;
            new Thread(() -> {
                long startTime2 = System.currentTimeMillis();
                for (int j = 0; j < 400000 ; j++) {
                    if (0 == n){
                        demo.write();
                    }else {
                        demo.read();
                    }
                }
            }).start();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
