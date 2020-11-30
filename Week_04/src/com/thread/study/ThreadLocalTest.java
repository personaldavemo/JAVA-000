package com.thread.study;

public class ThreadLocalTest {
    public static ThreadLocal<String> val = new ThreadLocal<>();
    @SuppressWarnings(value = "all")
    public void test() throws InterruptedException {
        val.set("Hello world");
        String v = val.get();
        System.out.println("value v:" + v);

        new Thread(() -> {
            String v2 = val.get();
            //线程级别的共享变量，跨线程无效
            System.out.println("value v2:" + v2);
            val.set("java");
            v2 = val.get();
            System.out.println("new v2:" + v2);
            System.out.println("done");
        }).start();
        Thread.sleep(5000L);
    }

    public static void main(String[] args) throws InterruptedException {
        new ThreadLocalTest().test();
        //局部变量，栈封闭
    }
}
