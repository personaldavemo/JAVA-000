package com.springstudy.aop.demo;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

@Aspect
public class EncoreableIntroducer {
    /**
     * 引入一个新方法，可以算作原有类的一个实现
     */
    @DeclareParents(value = "com.springstudy.aop.demo.Performance+",
    defaultImpl = EncoreablesImpl.class)
    public static Encoreables encoreables;
}
