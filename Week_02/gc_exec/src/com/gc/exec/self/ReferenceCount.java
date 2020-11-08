package com.gc.exec.self;

public class ReferenceCount {
    private Object instance = null;
    private static final int _1MB = 1024 * 1024;
    private byte[] bigSize = new byte[2 * _1MB];
    public static void testGC(){
        ReferenceCount a = new ReferenceCount();
        ReferenceCount b = new ReferenceCount();
        //循环引用，对象没有本完全回收
        a.instance = b;
        b.instance = a;
        a = null;
        b = null;
        System.gc();
    }

    public static void main(String[] args) {
        testGC();
    }
}
