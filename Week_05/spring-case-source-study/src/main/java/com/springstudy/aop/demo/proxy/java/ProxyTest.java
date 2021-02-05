package com.springstudy.aop.demo.proxy.java;

public class ProxyTest {
    public static void main(String[] args) {
        PersonProxy personProxy = new PersonProxy();
        Person person = personProxy.getProxy();
        person.walk();
    }
}
