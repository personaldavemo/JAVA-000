package com.thread.study.lock.demo.aqs1;

@SuppressWarnings("all")
public class TestMyReentrantLock {
    volatile static int i = 0;
    static MyReentrantLock lock = new MyReentrantLock();

    static void testAdd(){
        lock.lock();
        i++;
        lock.unlock();
    }
    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < 20; j++) {
            new Thread(() -> {
                for (int k = 0; k < 10000 ; k++) {
                    testAdd();
                }
                System.out.println(Thread.currentThread().getName() + " done.");
            }).start();
        }
        Thread.sleep(3000L);
        //预期20W
        System.out.println(i);
    }
}
