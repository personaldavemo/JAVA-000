package com.thread.study.atomic.demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Old A and New B
 * Compare old with memory same swap / or-else do nothing
 * cpu消耗
 * 仅针对单变量操作
 * ABA:T1同时执行多次操作（0，1）（1,0）T2(0,1)
 */
@SuppressWarnings("all")
public class CASDemo {
    volatile int i = 0;
    private static Unsafe unsafe = null;
    private static long varOffset;
    
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            Field fi = CASDemo.class.getDeclaredField("i");
            varOffset = unsafe.objectFieldOffset(fi);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void add(){
       for (;;){
           int cur = unsafe.getIntVolatile(this,varOffset);
           if (unsafe.compareAndSwapInt(this,varOffset,cur,cur+1)){
               break;
           }
       }
    }

    public static void main(String[] args) throws InterruptedException {
        final CASDemo demo = new CASDemo();
        for (int i = 0; i < 5 ; i++) {
            new Thread(() ->{
                for (int j = 0; j < 10000 ; j++) {
                    demo.add();
                }
                System.out.println(Thread.currentThread().getName() + "-add");
            }).start();
        }
        Thread.sleep(5000L);
        System.out.println(demo.i);
    }
}
