package com.thread.study.lock.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EasyCache {
    public static void main(String[] args) {
        GetCache cache = new GetCache();
        System.out.println(cache.getCache("Dave"));
    }

}
class GetCache{
    static volatile boolean cacheExist;
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public Object getCache(String key){
        Object data = null;
        lock.readLock().lock();
        try {
            if (cacheExist){
                data = LocalCache.cache.get(key);
            }else {
                //雪崩
                lock.readLock().unlock();
                //等待获取写锁
                lock.writeLock().lock();
                try {
                    if (!cacheExist) {
                        data = Db.query();
                        LocalCache.cache.put(key, data);
                        cacheExist = true;
                    }
                }finally {
                    //锁降级，持有写锁在获取读锁再释放写锁的过程，写锁降级为读锁
                    lock.readLock().lock();
                    lock.writeLock().unlock();
                }
            }
            return data;
        }finally {
            lock.readLock().unlock();
        }
    }
}
class Db{
    static String query(){
        return "Dave in db...";
    }
}
class LocalCache{
    static Map<String,Object> cache = new HashMap<>();
}