package com.springweb.demo.repository;

import com.springweb.demo.model.Spittle;

import java.util.List;

public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);
}
