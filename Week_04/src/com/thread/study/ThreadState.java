package com.thread.study;

/**
 * 线程状态
 * java.lang.Thread.State
 * new
 * Runnable
 * blocked--->锁定状态
 * waiting--->Object.wait()/Thread.join()/LockSupport.park()
 * Time-waiting--->Thread.sleep()/parkNanos/parkUtil
 * Terminated
 */
@SuppressWarnings(value = "all")
public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        /**
         * NEW ---> (start())RUNNABLE ---> TERMINATED
         */
        Thread t1 = new Thread(() -> {
            System.out.println("t1 state:" + Thread.currentThread().getState().toString());
            System.out.println("t1 do something");
        });
        System.out.println("t1 state:" + t1.getState().toString());
        t1.start();
        Thread.sleep(2000L);
        System.out.println("t1 state:" + t1.getState().toString());

        System.out.println("---------------------------------------------------");

        /**
         * new ---> runnable ---> TimedWaiting(sleep()) ---> runnable ---> terminated
         */
        Thread t2 = new Thread(() ->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 state:" + Thread.currentThread().getState().toString());
            System.out.println("t2 do something");
        });
        System.out.println("t2 state:" + t2.getState().toString());
        t2.start();
        System.out.println("t2 state:" + t2.getState().toString());
        Thread.sleep(200L);
        System.out.println("t2 state:" + t2.getState().toString());
        Thread.sleep(1300L);
        System.out.println("t2 state:" + t2.getState().toString());

        System.out.println("---------------------------------------------------");
        /**
         * t3和主线程抢锁
         * new ---> runnable ---> blocked ---> runnable ---> terminated
         */
        Thread t3 = new Thread(() ->{
            synchronized (ThreadState.class){
                System.out.println("t3 state:" + Thread.currentThread().getState().toString());
                System.out.println("t3 do something");
            }
        });
        synchronized (ThreadState.class){
            System.out.println("t3 state:" + t3.getState().toString());
            t3.start();
            System.out.println("t3 state:" + t3.getState().toString());
            Thread.sleep(200L);
            System.out.println("t3 state:" + t3.getState().toString());
        }
        Thread.sleep(2000L);
        System.out.println("t3 state:" + t3.getState().toString());
    }

}
