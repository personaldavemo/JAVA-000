package com.thread.study;
@SuppressWarnings(value = "all")
public class ThreadStopWithFlag extends Thread {
    private volatile static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() ->{
            while(flag){
                try {
                System.out.println("running");
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(2000L);
        flag = false;
    }
}
