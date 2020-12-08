package com.thread.study.lock.demo.aqs1;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class MyReadWriteLock {
    AtomicInteger readCount = new AtomicInteger(0);
    AtomicInteger writeCount = new AtomicInteger(0);
    public volatile LinkedBlockingDeque<WaitNode> waitSet = new LinkedBlockingDeque<>();
    AtomicReference<Thread> owner = new AtomicReference<>();

   private class WaitNode {
        /**
         * 0:写锁
         * 1.读锁
         */
        int type = 0;
        Thread thread = null;
        int updater = 0;

        public WaitNode(int type, Thread thread, int updater) {
            this.type = type;
            this.thread = thread;
            this.updater = updater;
        }
    }

    public void writeLock() {
        //拿写锁
        int updater = 1;
        if (!tryWriteLock(updater)) {
            //写锁进入等待队列
            WaitNode waitNode = new WaitNode(0, Thread.currentThread(), 1);
            waitSet.offer(waitNode);
            for (; ; ) {
                WaitNode head = waitSet.peek();
                if (null != head && head.thread == Thread.currentThread()) {
                    if (!tryWriteLock(updater)) {
                        LockSupport.park();
                    } else {
                        waitSet.poll();
                        return;
                    }
                } else {
                    LockSupport.park();
                }
            }
        }
    }

    private boolean tryWriteLock(int updater) {
        //readLock不为0则拿不到写锁
        if (readCount.get() != 0) {
            return false;
        }
        //拿到写锁
        int writers = writeCount.get();
        if (writers == 0) {
            if (writeCount.compareAndSet(writers, writers + updater)) {
                owner.set(Thread.currentThread());
                return true;
            }
            //写锁重入
        } else if (owner.get() == Thread.currentThread()) {
            writeCount.set(writers + updater);
            return true;
        }
        return false;
    }

    private boolean tryWriteUnLock(int updater) {
        if (owner.get() != Thread.currentThread()) {
            throw new IllegalMonitorStateException();
        }

        int writers = writeCount.get();
        int deWriters = writers - updater;
        writeCount.set(deWriters);
        if (deWriters == 0) {
            owner.compareAndSet(Thread.currentThread(), null);
            return true;
        } else {
            return false;
        }
    }

    public boolean unlockWrite() {
        int updater = 1;
        if (tryWriteUnLock(updater)) {
            WaitNode head = waitSet.peek();
            if (head != null) {
                Thread thread = head.thread;
                LockSupport.unpark(thread);
            }
            return true;
        }
        return false;
    }

    public void readLock() {
        int update = 1;
        if (tryReadLock(update) < 0) {
            WaitNode waitNode = new WaitNode(1,Thread.currentThread(),1);
            waitSet.offer(waitNode);
            for (;;){
                WaitNode head = waitSet.peek();
                if (head != null && head.thread == Thread.currentThread()){
                    if (tryReadLock(update) >= 0){
                        waitSet.poll();
                        //唤醒所有读线程
                        WaitNode next = waitSet.peek();
                        if (next != null && next.type == 1){
                            LockSupport.unpark(next.thread);
                        }
                        return;
                    }else {
                        LockSupport.park();
                    }
                }else {
                    LockSupport.park();
                }
            }
        }
    }

    public boolean unLockRead(){
        int updater = 1;
        if (tryReadUnLock(updater)){
            WaitNode head = waitSet.peek();
            if (head != null){
                LockSupport.unpark(head.thread);
            }
            return true;
        }
        return false;
    }

    private int tryReadLock(int update) {
        for (; ; ) {
            //占有写锁不是当前线程获取不到读锁
            if (writeCount.get() != 0 && owner.get() != Thread.currentThread()) {
                return -1;
            }
            int reads = readCount.get();
            if (readCount.compareAndSet(reads, reads + update)) {
                return 1;
            }
        }
    }

    private boolean tryReadUnLock(int update){
        for (;;){
            int reads = readCount.get();
            int deReads = reads - update;
            if (readCount.compareAndSet(reads,deReads)){
                return deReads == 0;
            }
        }
    }
}