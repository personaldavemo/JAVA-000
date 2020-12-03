package com.thread.study.lock.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
//        Thread t1 = new Thread(() ->{
//            lock.lock();
//            System.out.println("await()");
//            try {
//                //lock后调用,释放锁
//                condition.await();
//                System.out.println("running");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }finally {
//                lock.unlock();
//            }
//        });
//        t1.start();
//        Thread.sleep(2000L);
//        System.out.println("t1 state:" + t1.getState());
//        Thread.sleep(2000L);
//        lock.lock();
//        condition.signalAll();
//        lock.unlock();

        Thread t2 = new Thread(()->{
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                System.out.println("先唤醒死锁");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        t2.start();
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
