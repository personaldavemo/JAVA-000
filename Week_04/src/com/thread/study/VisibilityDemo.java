package com.thread.study;

/**
 * 可见性问题描述
 *注：JIT混合型编译:编译/解释
 */
public class VisibilityDemo {
    int i = 0;
    /**
     * 禁止cpu缓存，与指令重排序
     */
   volatile boolean flag = true;
    @SuppressWarnings(value = "all")
    public static void main(String[] args) throws InterruptedException {
        VisibilityDemo visibilityDemo = new VisibilityDemo();
        new Thread(() ->{
            //循环体第一次解释执行之后转为编译执行
            while (visibilityDemo.flag){
                visibilityDemo.i++;
            }
            System.out.println(visibilityDemo.i);
        }).start();
        Thread.sleep(3000L);
        visibilityDemo.flag = false;
        System.out.println("done");
    }
}
