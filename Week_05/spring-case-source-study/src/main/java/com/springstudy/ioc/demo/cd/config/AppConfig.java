package com.springstudy.ioc.demo.cd.config;

import com.springstudy.ioc.demo.cd.CompactDisc;
import com.springstudy.ioc.demo.cd.player.PcPlayer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("com.springstudy.ioc.demo.cd")
public class AppConfig {
    @Bean(name = "pcPlayer")
    @Primary
    public CompactDisc pcPlayer() {
        return  new PcPlayer();
    }
}
