package com.springstudy.ioc.demo.service;

import com.springstudy.ioc.demo.config.Office;
import org.springframework.stereotype.Component;

@Component
@Office
public class OfficeWorker implements Worker {
    @Override
    public void work() {
        System.out.println("working in office");
    }
}
