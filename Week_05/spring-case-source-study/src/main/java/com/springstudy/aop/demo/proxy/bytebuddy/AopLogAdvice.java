package com.springstudy.aop.demo.proxy.bytebuddy;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

public class AopLogAdvice {
    @Advice.OnMethodEnter
    public static void before(@Advice.Origin Method method, @Advice.AllArguments Object[] args) {
        System.out.println(method.getName() + " before print");
    }

    @Advice.OnMethodExit
    public static void after(@Advice.Origin Method method, @Advice.AllArguments Object[] args) {
        System.out.println(method.getName() + " after print");
    }
}
