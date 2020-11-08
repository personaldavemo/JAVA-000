package com.gc.exec.self;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;

    /**
     * -Xmx20M -XX:MaxDirectMemorySize=10M
     * nio激素hi分配直接内存
     */
    public static void main(String[] args) throws IllegalAccessException {
        Field unsafe = Unsafe.class.getDeclaredFields()[0];
        unsafe.setAccessible(true);
        Unsafe unsafe2 = (Unsafe) unsafe.get(null);
        while (true){
            unsafe2.allocateMemory(_1MB);
        }
    }
}
