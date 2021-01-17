package com.thread.study.lock.demo.aqs1;

import com.thread.study.lock.demo.aqs1.MyReadWriteLock;

public class                                                                                                                                                                                                                                                                                                                                                                                                          TestMyRwLock {
    static MyReadWriteLock lock = new MyReadWriteLock();
    static volatile int i = 0;
    static void testAdd(){
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < 100000 ; j++) {
            final int n = j;
            new Thread(() ->{
                if (n % 5 == 0){
                    lock.writeLock();
                    testAdd();
                    lock.unlockWrite();
                }else {

                    
                    lock.readLock();
                    System.out.println(i);
                    lock.unLockRead();
                }
            }).start();
        }
        while (true){
            Thread.sleep(1000L);
            System.out.println("res:" + i);
        }
    }
}
