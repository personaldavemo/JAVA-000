package com.thread.study;

import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTest {
    /**
     * 线程池测试方法
     * 提交20个任务
     */
    public void poolTest(ThreadPoolExecutor threadPoolExecutor) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            int n = i;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("run thread " + n);
                        //模拟执行2s
                        Thread.sleep(2000L);
                        System.out.println(n + " is done.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println(i + "任务提交");
        }
        Thread.sleep(500L);
        System.out.println("Thread in pool " + threadPoolExecutor.getPoolSize());
        System.out.println("Thread in waiting queue " + threadPoolExecutor.getQueue().size());
        Thread.sleep(10000L);
        System.out.println("Thread in pool " + threadPoolExecutor.getPoolSize());
        System.out.println("Thread in waiting queue " + threadPoolExecutor.getQueue().size());
    }

    public void poolTest2(ThreadPoolExecutor threadPoolExecutor) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            int n = i;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("run thread " + n);
                        //模拟执行2s
                        Thread.sleep(2000L);
                        System.out.println(n + " is done.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println(i + "任务提交");
        }
    }

    private void customPoolTest() throws InterruptedException {
        /**
         * 核心线程5，最大线程10，无界队列，超出核心线程的存活时间是5s
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        poolTest(threadPoolExecutor);
    }

    private void customPoolTest2() throws InterruptedException {
        /**
         * 队列大小是3
         * Max = 13
         * 有七个任务被拒绝
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,5L,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(3),
        new RejectedExecutionHandler(){

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.err.println("task rejected");
            }
        });
        poolTest(threadPoolExecutor);
    }

    private void myFixedThreadPool() throws InterruptedException {
        /**
         * NewFixedThreadPool 固定大小，核心线程数=最大线程数，无界队列
         * Executors.newFixedThreadPool(5)
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<Runnable>());
        poolTest(threadPoolExecutor);
    }

    private void myCachedThreadPool() throws InterruptedException {
        /**
         * newCachedThreadPool 核心线程数0，最大线程数Integer.MAX_VALUE,同步队列
         * 有空闲线程则执行，不然就创建一个新线程，线程超过60s泽被销毁
         * 线程随任务变化(异步任务，不可控)
         * 直接放入队列
         * Executors.newCachedThreadPool()
         * 同步队列：公平策略take-->tail.prev/head.next-->pull FIFO
         * 非公平的:FILO
         * 提交任务时，线程池没有空闲线程从队列取任务offer()则失败，则加开线程处理任务(<=Max)
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        poolTest(threadPoolExecutor);
    }

    private void mySingleThreadPool() throws InterruptedException {
        /**
         * newSinglePoolExecutor 单线程无界队列 核心线程 = 最大线程 = 1 （硬编码）
         * Executors.newSinglePoolExecutor()
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<Runnable>());
        poolTest(threadPoolExecutor);
    }

    private void myScheduledThreadPool() throws InterruptedException {
        /**
         * 指定定时任务的核心线程数，最大线程Integer.MAX_VALUE 延迟队列 非存活时间0L
         * Executors.newScheduledThreadPool(n);
         */
       ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("run task：" + System.currentTimeMillis());
            }
        },3000L,TimeUnit.MICROSECONDS);
        System.out.println("task done:" + System.currentTimeMillis() + " pool size:" + executor.getPoolSize());
    }


    private void myScheduledThreadPool2() throws InterruptedException {
        /**
         * 周期执行
         */
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                //任务执行耗时
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("task run:" + System.currentTimeMillis());
            }
            /**
             * task run:1606258180239
             * task run:1606258183250
             * task run:1606258186256
             * task run:1606258189264
             */
            //执行完毕立刻执行
        //任务时间>周期时间（任务时间为准）
        },2000L,1000L,TimeUnit.MICROSECONDS);


        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                //任务执行耗时
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("(withDelay)task run:" + System.currentTimeMillis());
            }
         //问题：延迟没有生效？
            /**
             * (withDelay)task run:1606258710368
             * (withDelay)task run:1606258713387
             * (withDelay)task run:1606258716407
             * (withDelay)task run:1606258719428
             * (withDelay)task run:1606258722445
             */
        },2000L,1000L,TimeUnit.MICROSECONDS);

    }

   private void poolStopTest() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,5L,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(5),
                new RejectedExecutionHandler(){

                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.err.println("task rejected");
                    }
                });
        poolTest2(threadPoolExecutor);
        Thread.sleep(1000L);
        //预期拒绝6个任务，等待正在执行的任务执行完毕，不能继续提交
        //threadPoolExecutor.shutdown();
       //立即停止，包括正在执行的线程
       List<Runnable> runnables = threadPoolExecutor.shutdownNow();
       threadPoolExecutor.submit(() ->{
            System.out.println("Are you stop??");
        });
       System.out.println(runnables.size());
    }


    public static void main(String[] args) throws InterruptedException {
        /**
         * run ---> !coreSizeMax ? new Thread : （!workQueueMax ? inQueue : （!MaxThread ? new Thread : 拒绝策略））
         */
//        new ThreadPoolTest().customPoolTest();
//        new ThreadPoolTest().customPoolTest2();
        //存在无界队列的时候，设置最大线程数没有意义
//        new ThreadPoolTest().myFixedThreadPool();
//        new ThreadPoolTest().myCachedThreadPool();
//        new ThreadPoolTest().mySingleThreadPool();
//        new ThreadPoolTest().myScheduledThreadPool();
//        new ThreadPoolTest().myScheduledThreadPool2();
        new ThreadPoolTest().poolStopTest();

    }
}
