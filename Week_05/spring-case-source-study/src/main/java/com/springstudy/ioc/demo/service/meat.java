package com.springstudy.ioc.demo.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class meat implements Food {
    @Override
    public void cook() {
        System.out.println("making meat");
    }
}
