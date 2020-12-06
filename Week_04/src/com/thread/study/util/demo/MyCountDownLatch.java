package com.thread.study.util.demo;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 计数器-到0执行某个行为
 */
public class MyCountDownLatch {
    private CountDownSync sync;
    public MyCountDownLatch(int count) {
        sync = new CountDownSync(count);
    }

    public void countDown(){
        sync.releaseShared(1);
    }

    public void await(){
        sync.acquireShared(1);
    }
}

class CountDownSync extends AbstractQueuedSynchronizer{
    public CountDownSync(int count){
        setState(count);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return getState() == 0 ? 1 : -1;
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        for (;;){
            int counter = getState();
            if (counter == 0){
                return false;
            }
            int deCount = counter -1;
            if (compareAndSetState(counter,deCount)){
                return deCount == 0;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyCountDownLatch countDownLatch = new MyCountDownLatch(5);
        for (int i = 0; i < 5 ; i++) {
            new Thread(()->{
                System.out.println("countdown() " + Thread.currentThread().getName());
                countDownLatch.countDown();
            }).start();
            Thread.sleep(1000L);
       }
        //执行完执行某些操作
        countDownLatch.await();
        System.out.println("done...");
    }
}
