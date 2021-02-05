package com.springstudy.aop.demo.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class CglibTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ProxyClass.class);
        enhancer.setCallback(new CglibProxy());
        ProxyClass proxyClass = (ProxyClass) enhancer.create();
        proxyClass.hello();
    }
}
