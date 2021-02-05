package com.springstudy.aop.demo.proxy.bytebuddy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AopLog {
}
