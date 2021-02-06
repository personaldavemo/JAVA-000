package com.springstudy.aop.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class AudienceAround {
    @Pointcut("execution(* com.springstudy.aop.demo.Performance.perform(..))")
    public void pointcut(){}

    @Around("pointcut()")
    public void performAround(ProceedingJoinPoint pjp) {
        try {
            System.out.println("Silencing cell phone");
            System.out.println("Taking seats");
            pjp.proceed();
            System.out.println("CLAP!!!!!");
        } catch (Throwable throwable) {
            System.out.println("Demanding a refund");
        }
    }
}
