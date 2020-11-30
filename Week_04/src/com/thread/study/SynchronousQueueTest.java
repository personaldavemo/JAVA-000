package com.thread.study;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueTest {
    @SuppressWarnings(value = "all")
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        Thread put = new Thread(() ->{
            System.out.println("put elemt...");
            try {
                synchronousQueue.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("put done...");
        });

        Thread take = new Thread(() ->{
            System.out.println("take elemt...");
            try {
                System.out.println(synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("put done...");
        });
        //阻塞
        put.start();
        Thread.sleep(2000L);
        //阻塞
        take.start();
    }
}
