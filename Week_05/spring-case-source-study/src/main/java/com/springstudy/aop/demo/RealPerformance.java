package com.springstudy.aop.demo;

import com.springstudy.aop.demo.Performance;
import org.springframework.stereotype.Component;

@Component
public class RealPerformance implements Performance {
    @Override
    public void perform() {
        System.out.println("play a song...");
//        throw new RuntimeException("cancel performance");
    }
}
