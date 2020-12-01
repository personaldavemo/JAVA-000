package com.thread.study.lock.demo;
@SuppressWarnings("all")
public class SynchronizedDemo {
    /**
     * 可重入
     * @throws InterruptedException
     */
    public static synchronized void res() throws InterruptedException {
        System.out.println("res by " + Thread.currentThread());
        Thread.sleep(500L);
        res();
        System.out.println("done");
    }



    public static void main(String[] args) throws InterruptedException {
        //res();
        Counter c1 = new Counter();
        Counter c2 = new Counter();

        new Thread(() ->{
            //Counter.addStatic();
            c1.add2();
        }).start();

        new Thread(() ->{
            //Counter.addStatic();
            c2.add2();
        }).start();
    }
}

class Counter{
    private static int i = 0;

    /**
     * c1,c2不互斥
     */
    public synchronized void add(){
        i++;
        System.out.println(i);
    }

    /**
     * c1,c2不互斥
     */
    public void add2(){
        //锁对象不互斥
        synchronized (this) {
            i++;
            System.out.println(i);
        }
    }

    public static synchronized void addStatic(){
        i++;
        System.out.println(i);
    }

    public static void addStatic2(){
        //类锁
        synchronized (Counter.class) {
            i++;
            System.out.println(i);
        }
    }
    //跨进程，分布式锁
}
