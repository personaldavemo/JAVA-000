package com.thread.study.lock.demo;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * ReentrantLock原理，自实现
 */
public class MyReentrantLock implements Lock {
    AtomicReference<Thread> owner = new AtomicReference<>();
    private LinkedBlockingDeque<Thread> waitSet = new LinkedBlockingDeque<>();
    AtomicInteger count = new AtomicInteger(0);
    @Override
    public void lock() {
        if (!tryLock()){
            //抢锁失败进入等待队列
            waitSet.offer(Thread.currentThread());
            for (;;){
                Thread head = waitSet.peek();
                if (head == Thread.currentThread()){
                    //队头抢锁
                    if (!tryLock()){
                        LockSupport.park();
                    }else {
                        //抢锁成功出队
                        waitSet.poll();
                        return;
                    }
                }else {
                    LockSupport.park();
                }
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        int counter = count.get();
        if (counter != 0){
            //重入
            if (owner.get() == Thread.currentThread()){
                count.set(counter + 1);
                return true;
            }else {
                return false;
            }
        }else {
            //cas(0,1)
            if (count.compareAndSet(counter,counter +1)){
                owner.set(Thread.currentThread());
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if (tryUnLock()){
            //解锁线程唤醒队列头线程
            Thread thread = waitSet.peek();
            if (null != thread){
                LockSupport.unpark(thread);
            }
        }
    }

    private boolean tryUnLock() {
        if (owner.get() != Thread.currentThread()){
            throw new IllegalMonitorStateException();
        }else {
            int counter = count.get();
            int deCounter = counter -1;
            count.set(deCounter);
            if (deCounter == 0){
                owner.compareAndSet(Thread.currentThread(),null);
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
