package com.springstudy.aop.demo.proxy.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;

public class ByteBuddyAop {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ProxyService proxyService = new ByteBuddy()
                .subclass(ProxyService.class)
                .method(ElementMatchers.any())
                //通知切面拦截
                .intercept(Advice.to(AopLogAdvice.class))
                .make()
                .load(ProxyService.class.getClassLoader())
                .getLoaded()
                .getConstructor()
                .newInstance();
        proxyService.msg();
    }
}
