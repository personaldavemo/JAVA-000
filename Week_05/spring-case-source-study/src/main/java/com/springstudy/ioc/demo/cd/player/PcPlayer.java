package com.springstudy.ioc.demo.cd.player;

import com.springstudy.ioc.demo.cd.CompactDisc;

public class PcPlayer implements CompactDisc {
    @Override
    public void play() {
        System.out.println("play with pc");
    }
}
