package com.springweb.demo.repository;

import com.springweb.demo.model.Spittler;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SpittleRepositoryImpl implements SpittleRepository {
    @Override
    public List<Spittler> findSpittles(long max, int count) {
        return createSplittles(count );
    }
    private List<Spittler> createSplittles(int i) {
        List<Spittler> spittles = new ArrayList<>();
        for (int j = 1; j < i ; j++) {
            Spittler spittle = Spittler.builder()
                    .id((long) j)
                    .message("spittle No:" + j)
                    .time(new Date())
                    .latitude(1112.334D)
                    .longtitude(2123.1235D)
                    .build();
            spittles.add(spittle);
        }
        return spittles;
    }
}
