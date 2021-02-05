package com.springstudy.aop.demo.proxy.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class HelloWorld {
    public void hello() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                //找到目标类
                .subclass(Object.class)
                //修改目标方法
                .method(ElementMatchers.named("toString"))
                //拦截
                .intercept(FixedValue.value("Hello world"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
        System.out.println(dynamicType.newInstance().toString());
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        new HelloWorld().hello();
    }
}
