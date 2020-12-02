package com.thread.study.atomic.demo.aba;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;


public class Stack {
    AtomicReference<Node> top = new AtomicReference<>();
    public void push(Node node){
        Node old;
        do {
            old = top.get();
            node.next = old;
        }while (!top.compareAndSet(old,node));
    }

    public Node pop(int time){
        Node newTop;
        Node old;
        do {
            old = top.get();
            if (null == old){
                return null;
            }
            newTop = old.next;
            if (0 != time){
                LockSupport.parkNanos(1000 * 1000 * time);
            }
        }while (!top.compareAndSet(old,newTop));

        return old;
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(new Node("A"));
        stack.push(new Node("B"));

        Thread t1 = new Thread(() ->{
            System.out.println("pop t1");
            Node node = stack.pop(800);
            System.out.println(Thread.currentThread().getName() + "---" + node.toString());
            System.out.println("t1 done.");
        });
        t1.start();

        Thread t2 = new Thread(() ->{
            LockSupport.parkNanos(1000 * 1000 * 300L);
            Node b = stack.pop(0);
            System.out.println(Thread.currentThread().getName() + "---" + b.toString());

            Node a = stack.pop(0);
            System.out.println(Thread.currentThread().getName() + "---" + a.toString());

            stack.push(new Node("C"));
            stack.push(new Node("D"));
            stack.push(b);

            System.out.println("t2 done");
        });

        t2.start();

        LockSupport.parkNanos(1000 * 1000 * 1000 * 2L);

        System.out.println("print stack");
        Node node = null;
        while (null != (node = stack.pop(0))){
            System.out.println(node.value);
        }
    }
}