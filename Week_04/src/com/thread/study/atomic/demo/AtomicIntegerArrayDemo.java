package com.thread.study.atomic.demo;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicIntegerArrayDemo {
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(3);
        atomicIntegerArray.set(1,12);
        //对每个元素单独cas
        atomicIntegerArray.compareAndSet(1,13,14);
        //指向新对象
        AtomicReference<Thread> atomicReference = new AtomicReference<>();
        atomicReference.get();
        atomicReference.compareAndSet(null,Thread.currentThread());
    }
}
