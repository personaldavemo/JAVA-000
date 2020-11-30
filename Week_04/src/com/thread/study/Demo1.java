package com.thread.study;

/**
 * sleep()不释放锁,TimeWaiting
 * yield()
 * join(),等待其他线程执行完毕再进入runnable,当前线程锁不释放
 * wait() 释放锁，进入waitSet
 * notify()/notifyAll()
 */
public class Demo1 {
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread t = Thread.currentThread();
                System.out.println("当前线程：" + t.getName());
            }
        };
        Thread thread = new Thread(task);
        thread.setName("test-thread-1");
        thread.setDaemon(true);
        thread.start();
    }
}