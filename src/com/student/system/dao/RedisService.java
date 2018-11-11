package com.student.system.dao;

import redis.clients.jedis.Jedis;

import java.util.Set;

public final class RedisService {
    private static RedisService instance;
    private final String address = "192.168.31.4";
    private Jedis jedis;

    private static synchronized RedisService getInstance() {
        if (instance == null) {
            instance = new RedisService();
        }
        return instance;
    }

    private RedisService() {
        try {
            jedis = new Jedis(address);
            if ("PONG".equals(jedis.ping())) {
                System.out.println("Redis服务连接成功!");
            } else {
                new Exception("Redis连接失败,程序退出");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public void put(String key, String value) {
        jedis.sadd(key, value);
    }

    public Set<String> get(String key) {
        if (jedis.exists(key)) {
            return jedis.smembers(key);
        } else {
            return null;
        }
    }
    public long scard(String key)
    {
        if(jedis.exists(key))
        {
            return jedis.scard(key);
        }
        else
            return 0;
    }
    public boolean remove(String key,String value)
    {
        if (jedis.exists(key))
        {
            jedis.srem(key,value);
            return true;
        }
        return false;
    }
}
