package com.springweb.demo.repository;

import com.springweb.demo.model.Spittler;

import java.util.List;

public interface SpittleRepository {
    List<Spittler> findSpittles(long max, int count);
}
