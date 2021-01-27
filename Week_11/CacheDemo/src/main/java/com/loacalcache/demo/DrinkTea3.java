package com.loacalcache.demo;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DrinkTea3 {

    static class T1 implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println(Thread.currentThread().getName() + "烧水");
                Thread.sleep(500L);
                System.out.println(Thread.currentThread().getName() + "水开了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    static class T2 implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println(Thread.currentThread().getName() + "洗茶具");
                Thread.sleep(500L);
                System.out.println(Thread.currentThread().getName() + "洗完了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    static  class MasterWork implements Runnable {
        boolean water = false;
        boolean wash = false;
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(50L);
                    System.out.println("等待泡茶");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int flag = getResult(water, wash);
                if (flag == 1){
                    break;
                }

            }
        }
    }

    public static int getResult(boolean b1, boolean b2) {
        if (b1 && b2) {
            System.out.println("喝茶");
            return 1;
        } else if (!b1) {
            System.out.println("烧水失败");
            return -1;
        } else {
            System.out.println("洗杯子失败");
            return -2;
        }
    }

    public static void main(String[] args) {
        MasterWork masterWork = new MasterWork();
        Thread master = new Thread(masterWork);
        master.start();

        Callable<Boolean> waterCall = new T1();
        Callable<Boolean> washCall = new T2();

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        //Guava包装线程池
        ListeningExecutorService servicePool = MoreExecutors.listeningDecorator(threadPool);
        ListenableFuture<Boolean> waterFuture = servicePool.submit(waterCall);
        //烧水任务完成回调
        Futures.addCallback(waterFuture,new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    masterWork.water = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable1) {
                System.out.println("烧水失败");
            }
        }, threadPool);

        ListenableFuture<Boolean> washFuture = servicePool.submit(washCall);
        //烧水任务完成回调
        Futures.addCallback(waterFuture,new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean bBoolean) {
                if (bBoolean) {
                    masterWork.wash = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable2) {
                System.out.println("烧水失败");
            }
        }, threadPool);
    }

}
