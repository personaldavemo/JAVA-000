package com.thread.study;

/**
 * final修饰的变量或者对象的正确构造版本
 */
@SuppressWarnings("all")
public class FinalDemo {
    final int a;
    int b;
    static FinalDemo obj;

    public FinalDemo(){
        a = 1;
        b = 2;
    }

    static void init(){
        obj = new FinalDemo();
    }

    static void reader(){
        if (null != obj){
            int i = obj.a;
            int j = obj.b; //这个值就不一定是2了
            System.out.println(i + "," + j);
        }
    }

    public static void main(String[] args) {
        new Thread(()->{
            init();
        }).start();

        new Thread(() ->{
            reader();
        }).start();
    }
}
