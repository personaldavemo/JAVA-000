package com.springstudy.ioc.demo.cd.player;

import com.springstudy.ioc.demo.cd.CompactDisc;
import org.springframework.stereotype.Component;

@Component
public class SgtPeppers implements CompactDisc {
    @Override
    public void play() {
        System.out.println("playing cd");
    }
}
