package com.springsudy.ioc.demo.cd;

import com.springstudy.ioc.demo.cd.CompactDisc;
import com.springstudy.ioc.demo.cd.MediaPlayer;
import com.springstudy.ioc.demo.cd.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CDTest {
    @Qualifier("sgtPeppers")
    @Autowired
    private CompactDisc compactDisc;
    @Autowired
    private MediaPlayer mediaPlayer;

    @Qualifier("pcPlayer")
    @Autowired
    private CompactDisc pcPlayer;

    @Test
    public void playTest() {
        compactDisc.play();
    }

    @Test
    public void MediaPlayerTest() {
        mediaPlayer.playVideo();
    }

    @Test
    public void pcPlayerTest() {
        pcPlayer.play();
    }
}
