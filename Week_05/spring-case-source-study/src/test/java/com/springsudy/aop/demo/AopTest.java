package com.springsudy.aop.demo;


import com.springstudy.aop.demo.Encoreables;
import com.springstudy.aop.demo.Performance;
import com.springstudy.aop.demo.cd.CdConfig;
import com.springstudy.aop.demo.cd.CdPlayer;
import com.springstudy.aop.demo.cd.TracksCounter;
import com.springstudy.aop.demo.config.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, CdConfig.class})
public class AopTest {
    @Autowired
    private Performance performance;
    @Autowired
    @Qualifier("blankDisc")
    private CdPlayer cdPlayer;
    @Autowired
    public TracksCounter tracksCounter;


    @Test
    public void testAop() {
        performance.perform();
    }

    @Test
    public void testIntroducer() {
        Encoreables encoreables = (Encoreables) performance;
        encoreables.performEncore();
        performance.perform();
    }

    @Test
    public void testCdCounter() {
        cdPlayer.play(1);
        cdPlayer.play(2);
        cdPlayer.play(3);
        cdPlayer.play(3);
        cdPlayer.play(3);
        cdPlayer.play(3);
        cdPlayer.play(7);
        cdPlayer.play(7);

        Assert.assertEquals(1, tracksCounter.getPlayCount(1));
        Assert.assertEquals(1, tracksCounter.getPlayCount(2));
        Assert.assertEquals(4, tracksCounter.getPlayCount(3));
        Assert.assertEquals(2, tracksCounter.getPlayCount(7));
    }
}
