package com.springstudy.aop.demo;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Audience {

    @Before("execution(* com.springstudy.aop.demo.Performance.perform(..))")
    public void silenceCellPhone() {
        System.out.println("Silencing cell phone");
    }

    @Before("execution(* com.springstudy.aop.demo.Performance.perform(..))")
    public void takeSeats() {
        System.out.println("Taking seats");
    }

    @AfterReturning("execution(* com.springstudy.aop.demo.Performance.perform(..))")
    public void applause() {
        System.out.println("CLAP!!!!!");
    }

    @AfterThrowing("execution(* com.springstudy.aop.demo.Performance.perform(..))")
    public void demandRefund() {
        System.out.println("Demanding a refund");
    }
}
