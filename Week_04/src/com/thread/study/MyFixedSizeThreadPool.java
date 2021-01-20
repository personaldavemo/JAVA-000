package com.thread.study;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class MyFixedSizeThreadPool {

    private BlockingDeque<Runnable> task;

    private List<Worker> workers;

    private volatile boolean working = true;

    public MyFixedSizeThreadPool(int poolSize, int taskSize) {
        if (poolSize <= 0 || taskSize <= 0) {
            throw new IllegalArgumentException("params error");
        }

        this.task = new LinkedBlockingDeque<>(taskSize);

        this.workers = Collections.synchronizedList(new LinkedList<>());

        for (int i = 0; i < poolSize ; i++) {
            Worker worker = new Worker(this);
            this.workers.add(worker);
            worker.start();
        }
    }

    public boolean submit(Runnable runnable) {
        //入队，队满阻塞
        return this.task.offer(runnable);
    }

    public void shutdown() {
        if (this.working) {
            this.working = false;

            for (Thread thread : this.workers) {
                if (thread.getState().equals(Thread.State.BLOCKED) || thread.getState().equals(Thread.State.WAITING)) {
                    thread.interrupt();
                }
            }
        }
    }

    private class Worker extends Thread {

        private MyFixedSizeThreadPool fixedSizeThreadPool;

        public Worker(MyFixedSizeThreadPool fixedSizeThreadPool) {
            this.fixedSizeThreadPool = fixedSizeThreadPool;
        }

        @Override
        public void run() {
            int counter = 0;
            while (this.fixedSizeThreadPool.working || this.fixedSizeThreadPool.task.size() > 0) {
                Runnable r = null;

                try {
                    if (this.fixedSizeThreadPool.working) {
                        r = this.fixedSizeThreadPool.task.take();
                    } else {
                        r = this.fixedSizeThreadPool.task.poll();
                    }
                } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                if (r != null) {
                    r.run();
                    System.out.println(Thread.currentThread().getName() + "执行完成" + (++counter) + "个任务" );
                }
            }
            System.out.println(Thread.currentThread().getName() + "执行结束");
        }
    }
}
