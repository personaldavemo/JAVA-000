package com.gc.exec.self;

public class TestSOF {
    private int stackLength = 1;
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    /**
     *-Xss180k
     */
    public static void main(String[] args) {
        TestSOF test = new TestSOF();
        try {
            test.stackLeak();
        }catch (Throwable e){
            System.out.println("递归了："+test.stackLength);
            throw e;
        }
    }
}
