package com.springstudy.aop.demo.cd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;


@Configuration
@ComponentScan(basePackages = "com.springstudy.aop.demo.cd")
@EnableAspectJAutoProxy
public class CdConfig {
    @Bean
    public TracksCounter tracksCounter() {
        return new TracksCounter();
    }

    @Bean("blankDisc")
    public CdPlayer cdPlayer() {
        BlankDisc blankDisc = BlankDisc.builder()
                .name("Sgt. Pepper's Lonely Hearts Club Band")
                .artist("The Beatles")
                .tracks(Arrays.asList("Sgt. Pepper's Lonely Hearts Club Band",
                        "With a Little Help from My Friends",
                        "Lucy in the Sky with Diamonds",
                        "Getting Better",
                        "Fixing a Hole"))
                .build();
        return blankDisc;
    }
}
