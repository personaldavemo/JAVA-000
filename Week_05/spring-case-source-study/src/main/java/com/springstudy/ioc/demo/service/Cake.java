package com.springstudy.ioc.demo.service;

import org.springframework.stereotype.Component;

@Component
public class Cake implements Food {
    @Override
    public void cook() {
        System.out.println("making cake");
    }
}
