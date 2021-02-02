package com.springstudy.ioc.demo.run;

import com.springstudy.ioc.demo.service.ActionService;
import com.springstudy.ioc.demo.service.HelloService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DIRun1 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-ioc-base-config.xml");
        HelloService service = context.getBean("helloService", HelloService.class);
        String s = service.sayHello();
        System.out.println(s);

        System.out.println("----------------------------------------");
        ActionService actionService = context.getBean(ActionService.class);
        actionService.walk();
        System.out.println(actionService);
    }
}
