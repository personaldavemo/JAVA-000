package com.thread.study.lock.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EasySafeHashMap {
    private final Map<String,Object> map = new HashMap<>();
    //HashTable
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public Object get(String key){
        readLock.lock();
        try {
            return map.get(key);
        }finally {
            readLock.unlock();
        }
    }

    public Object[] getKeys(){
        readLock.lock();
        try {
            return map.keySet().toArray();
        }finally {
            readLock.unlock();
        }
    }

    public Object put(String key,Object obj){
        writeLock.lock();
        try {
            return map.put(key,obj);
        }finally {
            writeLock.unlock();
        }
    }

    public void clear(){
        writeLock.lock();
        try {
            map.clear();
        }finally {
            writeLock.unlock();
        }
    }
}
