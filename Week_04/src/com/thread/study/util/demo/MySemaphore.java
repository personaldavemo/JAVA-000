package com.thread.study.util.demo;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 共享资源限流器
 */
public class MySemaphore {
    private SemaphoreSync sync;

    public MySemaphore(int permit) {
        sync = new SemaphoreSync(permit);
    }

    public void acquire(){
        sync.acquireShared(1);
    }

    public void release(){
        sync.releaseShared(1);
    }
}
class SemaphoreSync extends AbstractQueuedSynchronizer{
    private int permits;

    public SemaphoreSync(int permits){
        this.permits = permits;
    }

    @Override
    protected int tryAcquireShared(int arg) {
        int state = getState();
        int addState = state + arg;

        if (addState <= permits){
            if (compareAndSetState(state,addState)){
                return 1;
            }
        }
        return -1;
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        int state =getState();
        if (compareAndSetState(state,state - arg)){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        MySemaphore semaphore = new MySemaphore(5);
        for (int i = 0; i < 1000 ; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //5 5 5 5~
                System.out.println("do something");
                semaphore.release();
            }).start();

        }
    }
}
