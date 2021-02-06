package com.springstudy.aop.demo;

/**
 * 主题：切面的切点
 * 切点配置
 * execution(* com.springstudy.aop.demo.perform(..))
 * 仅匹配某个包
 * execution(* com.springstudy.aop.demo.perform(..) && within(com.springstudy.aop.*)
 * bean匹配
 * execution(* com.springstudy.aop.demo.perform(..) and bean('myBean')
 */
public interface Performance {
    void perform();
}
