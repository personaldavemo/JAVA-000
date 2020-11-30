package com.thread.study.atomic.demo;

import java.util.concurrent.atomic.LongAccumulator;

public class LongAccumulatorDemo {
    public static void main(String[] args) {
        LongAccumulator accumulator = new LongAccumulator(
                (x,y) -> {
                    System.out.println(x + "," + y);
                    return x + y;
                }
        ,0L);
        /**
         * 0,1
         * 1,1
         * 2,1
         */

        for (int i = 0; i < 3 ; i++) {
            accumulator.accumulate(1);
        }

        System.out.println(accumulator.get());
    }
}
