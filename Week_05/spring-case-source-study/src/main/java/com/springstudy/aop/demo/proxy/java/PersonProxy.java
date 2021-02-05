package com.springstudy.aop.demo.proxy.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PersonProxy {

    private Person tom = new Tom();

   public Person getProxy() {
       return (Person) Proxy.newProxyInstance(PersonProxy.class.getClassLoader(), tom.getClass().getInterfaces(),
               new InvocationHandler() {
                   @Override
                   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                       System.out.println("Tom put on close..");
                       return method.invoke(tom,args);
                   }
               });
   }
}
