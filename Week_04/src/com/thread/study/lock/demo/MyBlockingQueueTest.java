package com.thread.study.lock.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        MyBlockingQueue queue = new MyBlockingQueue(5);
        System.out.println("put start");
        new Thread(() ->{
            for (int i = 0; i < 10 ; i++) {
                queue.put("elem" + i);
            }
        }).start();

        Thread.sleep(3000L);
        System.out.println("get start");
        for (int i = 0; i < 10 ; i++) {
            queue.get();
            Thread.sleep(3000L);
        }
    }
}

/**
 * 队列满put阻塞
 * 队列空get阻塞
 */
class MyBlockingQueue{
    List<Object> data = new ArrayList<>();
    Lock lock = new ReentrantLock();
    Condition put = lock.newCondition();
    Condition get = lock.newCondition();
    private int length;

    public MyBlockingQueue(int length) {
        this.length = length;
    }

    public void put(Object obj){
        lock.lock();
        try {
            if (data.size() < length){
                data.add(obj);
                System.out.println("put:" + obj);
                get.signal();
            }else {
                put.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public Object get(){
        lock.lock();
        Object obj = null;
        try {
            for (;;){
                if (data.size() > 0){
                    obj = data.get(0);
                    data.remove(0);
                    System.out.println("get:" + obj);
                    put.signal();
                    break;
                }else {
                    get.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return obj;
    }
}
