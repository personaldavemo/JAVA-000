package com.springstudy.aop.demo.proxy.bytebuddy;

public class ProxyService {
    @AopLog
    public void msg() {
        System.out.println("print msg");
    }
}
