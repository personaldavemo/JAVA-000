package com.springstudy.aop.demo.cd;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Builder
@Component
public class BlankDisc implements CdPlayer {
    private String name;
    private String artist;
    private List<String> tracks;


    @Override
    public void play(int num) {
        System.out.println("play song:" + name + " wrote by " + artist + ",and tracks num is:" + num);
    }
}
