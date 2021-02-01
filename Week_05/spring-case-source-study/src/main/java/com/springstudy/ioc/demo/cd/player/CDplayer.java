package com.springstudy.ioc.demo.cd.player;

import com.springstudy.ioc.demo.cd.CompactDisc;
import com.springstudy.ioc.demo.cd.MediaPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CDplayer implements MediaPlayer {

    private CompactDisc compactDisc;

    @Autowired(required = false)
    public CDplayer(CompactDisc compactDisc) {
        this.compactDisc = compactDisc;
    }

    @Override
    public void playVideo() {
        compactDisc.play();
    }
}
