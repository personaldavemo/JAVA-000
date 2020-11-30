package com.thread.study;
@SuppressWarnings(value = "all")
public class ThreadStop extends Thread {
    private int i = 0,j = 0;
    private Thread print;

    @Override
    public void run() {
        /**
         * 模拟原子操作
         */
        synchronized (this){
            ++i;
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++j;
        }
    }

    public void print(){
        System.out.println("i = " + i + " j = " + j);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadStop test = new ThreadStop();
        test.start();
        Thread.sleep(1000L);
        //不能保证原子性
        //test.stop();
        /**
         * 正确中断线程的方式interrupt(),wait()/join()/sleep()/park()
         * InterruptedException
         * 或者I/O阻塞
         * 标志位
         */
        test.interrupt();//清除中断状态
        System.out.println(test.isInterrupted());
//        System.out.println(Thread.interrupted());
//        System.out.println(test.isInterrupted());
        while (test.isAlive()){}
        test.print();

    }
}
