package com.gc.exec.self;

public class FinalizeEscapeGC {
    private static  FinalizeEscapeGC SAVE_HOOK = null;
    public void isAlive(){
        System.out.println("alive");
    }

    @Override
    protected void finalize() throws Throwable {
        /**
         * 只执行一次
         * 将要回收的对象放在F-Queue
         * 可以被重新关联
         */
        super.finalize();
        System.out.println("finalize executed...");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500L);
        if (SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("gc dead...");
        }

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500L);
        if (SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("gc dead...");
        }
    }
}
