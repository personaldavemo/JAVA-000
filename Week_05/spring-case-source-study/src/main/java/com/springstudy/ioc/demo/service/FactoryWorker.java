package com.springstudy.ioc.demo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("factory")
public class FactoryWorker implements Worker {
    @Override
    public void work() {
        System.out.println("working in factory");
    }
}
