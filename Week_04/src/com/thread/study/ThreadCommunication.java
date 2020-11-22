package com.thread.study;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.LockSupport;

public class ThreadCommunication {
    //共享变量
    private static String content = "-";
    //商品
    private static Object goods = null;

    private static Object lock = new Object();

    /**
     * 文件共享
     */
    public static void fileShare(){
        /**
         * 写入数据
         */
        new Thread(() -> {
            try {
                while(true){
                    Files.write(Paths.get("fileShare.log"),("当前时间" + String.valueOf(System.currentTimeMillis())).getBytes());
                    Thread.sleep(1000L);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
        /**
         * 读取数据
         */
        new Thread(() -> {
            try {
                while(true){
                    Thread.sleep(1000L);
                    byte[] allBytes = Files.readAllBytes(Paths.get("fileShare.log"));
                    System.out.println(new String(allBytes));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 共享变量
     */
    public static void shareParams(){
        new Thread(() -> {
            try {
                while (true){
                    content = "当前时间" + String.valueOf(System.currentTimeMillis());
                    Thread.sleep(1000L);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true){
                    Thread.sleep(1000L);
                    System.out.println(content);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 生产者，消费者
     */
    public static void consumerProduct() throws InterruptedException {
        /**
         * suspend / resume
         * 很容易造成死锁
         * 同步代码块不释放锁
         * resume先于suspend执行
         */
//        Thread consumer = new Thread(() ->{
//            if (goods == null){
//                System.out.println("waiting goods");
//                Thread.currentThread().suspend();
//            }
//            System.out.println("get goods...");
//        });
//
//        consumer.start();
//
//        Thread.sleep(3000L);
//        goods = new Object();
//        consumer.resume();
//        System.out.println("notify consumer");



        //死锁情况1:
//        Thread consumer = new Thread(() ->{
//            if (goods == null){
//                System.out.println("waiting goods");
//                //死锁原因：suspend不释放锁
//                synchronized (lock) {
//                    Thread.currentThread().suspend();
//                }
//            }
//            System.out.println("get goods...");
//        });
//
//        consumer.start();
//
//        Thread.sleep(3000L);
//        goods = new Object();
//        //拿不锁
//        synchronized (lock) {
//            consumer.resume();
//        }
//        System.out.println("notify consumer");


//死锁情况2：resume先于suspend执行
//                Thread consumer = new Thread(() ->{
//            if (goods == null){
//                System.out.println("waiting goods");
//                //模拟消费者5秒后挂起
//                try {
//                    Thread.sleep(5000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Thread.currentThread().suspend();
//            }
//            System.out.println("get goods...");
//        });
//
//        consumer.start();
//
//        Thread.sleep(3000L);
//        goods = new Object();
//        consumer.resume();
//        System.out.println("notify consumer");
         //consumer.join();

        /**
         * wait/notify/notifyAll
         * 会释放锁
         * notify先于wait执行依旧会死锁
         */
//        Thread consumer = new Thread(() ->{
//            if (goods == null){
//                //死锁情况
////                try {
////                    Thread.sleep(5000L);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                synchronized (lock) {
//                System.out.println("waiting goods");
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            System.out.println("get goods...");
//        });
//
//        consumer.start();
//
//        Thread.sleep(3000L);
//        goods = new Object();
//
//        synchronized (lock) {
//            lock.notifyAll();
//        System.out.println("notify consumer");
//        }

        /**
         * park/unpark
         * 等待许可,提供许可
         * 无顺序限制
         * 多次unpark，再次调用park直接运行
         * 连续调用park,第一次get到许可，后续则等待
         * 也不释放锁，和监视器无关（死锁）
         */
//                Thread consumer = new Thread(() ->{
//            if (goods == null){
//                System.out.println("waiting goods");
//                    LockSupport.park();
//                }
//            System.out.println("get goods...");
//        });
//
//        consumer.start();
//
//        Thread.sleep(3000L);
//        goods = new Object();
//        //给指定线程许可
//        LockSupport.unpark(consumer);
//        System.out.println("notify consumer");

//        Thread consumer = new Thread(() ->{
//            /**
//             * if判断有伪唤醒问题
//             */
//            if (goods == null){
//                synchronized (lock) {
//                    System.out.println("waiting goods");
//                    LockSupport.park();
//                }
//            }
//            System.out.println("get goods...");
//        });
//
//        consumer.start();
//
//        Thread.sleep(3000L);
//        goods = new Object();
//        //给指定线程许可
//        synchronized (lock) {
//            LockSupport.unpark(consumer);
//            System.out.println("notify consumer");
//        }

        Thread consumer = new Thread(() ->{
            while (goods == null){
                System.out.println("waiting goods");
                LockSupport.park();
            }
            System.out.println("get goods...");
        });

        consumer.start();

        Thread.sleep(3000L);
        goods = new Object();
        //给指定线程许可
        LockSupport.unpark(consumer);
        System.out.println("notify consumer");

    }


    public static void main(String[] args) throws InterruptedException {
        //fileShare();
       //shareParams();
        consumerProduct();
    }
}
