package com.springsource.study.demo.test;

import com.springsource.study.demo.model.Car;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryBeanTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-source-ioc_1.xml");
        /**
         * FactoryBean# #getObject()
         * #applyBeanPostProcessorsAfterInitialization初始化后置处理
         */
        Car car = (Car) context.getBean("car");
        System.out.println(car);
    }
}
