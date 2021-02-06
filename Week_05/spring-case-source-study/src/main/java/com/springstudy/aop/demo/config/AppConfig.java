package com.springstudy.aop.demo.config;

import com.springstudy.aop.demo.Audience;
import com.springstudy.aop.demo.AudienceAround;
import com.springstudy.aop.demo.AudienceWithPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.springstudy.aop.demo")
@EnableAspectJAutoProxy
public class AppConfig {

//    @Bean
//    public AudienceWithPointcut audienceWithPointcut() {
//        return new AudienceWithPointcut();
//    }

//    @Bean
//    public Audience audience() {
//        return new Audience();
//    }

    @Bean
    public AudienceAround audienceAround() {
        return new AudienceAround();
    }
}
