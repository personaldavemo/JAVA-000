package com.springstudy.aop.demo;

import org.aspectj.lang.annotation.*;

@Aspect
public class AudienceWithPointcut {

    @Pointcut("execution(* com.springstudy.aop.demo.Performance.perform(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void silenceCellPhone() {
        System.out.println("Silencing cell phone");
    }

    @Before("pointcut()")
    public void takeSeats() {
        System.out.println("Taking seats");
    }

    @AfterReturning("pointcut()")
    public void applause() {
        System.out.println("CLAP!!!!!");
    }

    @AfterThrowing("pointcut()")
    public void demandRefund() {
        System.out.println("Demanding a refund");
    }
}
