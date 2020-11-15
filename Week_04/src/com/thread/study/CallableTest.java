package com.thread.study;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("do something...after return");
        return "Hello";
    }

    public static void main(String[] args) {
        CallableTest test = new CallableTest();
        FutureTask<String> futureTask = new FutureTask<>(test);
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
