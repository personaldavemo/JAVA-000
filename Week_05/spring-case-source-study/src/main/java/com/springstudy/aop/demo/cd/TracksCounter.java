package com.springstudy.aop.demo.cd;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class TracksCounter {
    /**NumOfTracks,playCount*/
    private Map<Integer, Integer> tracksCounter = new HashMap<>();

    @Pointcut("execution(* com.springstudy.aop.demo.cd.CdPlayer.play(int)) && args(trackNum)")
    public void tracksPlay(int trackNum){}

    @Before("tracksPlay(trackNum)")
    public void countTrack(int trackNum) {
        int curTrack = getPlayCount(trackNum);
        tracksCounter.put(trackNum, curTrack + 1);
    }

    public int getPlayCount(int trackNum) {
        return tracksCounter.containsKey(trackNum) ? tracksCounter.get(trackNum) : 0;
    }
}
