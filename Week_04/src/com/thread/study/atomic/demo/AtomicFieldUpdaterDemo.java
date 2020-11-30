package com.thread.study.atomic.demo;


import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicFieldUpdaterDemo {
    private static AtomicIntegerFieldUpdater<Person> updater = AtomicIntegerFieldUpdater.newUpdater(Person.class,"id");

    public static void main(String[] args) {
        Person tom = new Person("Tom", 121, 111);
        updater.addAndGet(tom,20);
        System.out.println(tom);
    }
}


class Person{
    private String name;
    volatile int id;
    volatile int age;

    public Person(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                '}';
    }
}