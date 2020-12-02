package com.thread.study.lock.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@SuppressWarnings("all")
public class LockApiTest {
    //非公平锁
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        lock.lock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("get lock");
                /**
                 * 会一直等到锁释放取到锁为止
                 */
//                lock.lock();
//                System.out.println("get it");
                /**
                 * 尝试获取锁，如果当前锁被占用直接返回fasle
                 */
//                boolean isGet = lock.tryLock();
//                System.out.println(isGet);

                /**
                 * 指定时间去获得所
                 */
//                try {
//                    boolean getWithTime = lock.tryLock(6, TimeUnit.SECONDS);
//                    System.out.println(getWithTime);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                /**
                 * 外部通知停止则不再获取锁
                 */
//                try {
//                    lock.lockInterruptibly();
//                } catch (InterruptedException e) {
//                    //e.printStackTrace();
//                    System.out.println("someone call me stop");
//                }

                System.out.println("get lock");
                lock.lock();
                /**
                 * ！！！如果T1被中断了获取不到这个中断的信息。
                 */
            }
        });
        t1.start();
        t1.interrupt(); //停止t1
        Thread.sleep(2000L);
        lock.unlock();
    }
}
