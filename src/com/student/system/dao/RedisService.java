package com.student.system.dao;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 本类提供了对于Redis服务的访问,单例实现
 *
 * @author Diaz
 * @version 1
 * @since 2018-11-11
 */
public final class RedisService {
    private static RedisService instance;
    private final String address = "192.168.31.4";
    private Jedis jedis;

    /**
     * 获取本类的唯一实例
     *
     * @return 本类的唯一实例
     */
    public static synchronized RedisService getInstance() {
        if (instance == null) {
            instance = new RedisService();
        }
        return instance;
    }

    /**
     * 构造函数
     */
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

    /**
     * 向Set储存一个值
     *
     * @param key   Set对应的键
     * @param value 要储存的值
     */
    public void putSet(String key, String value) {
        jedis.sadd(key, value);
    }

    /**
     * 获取一个Set
     *
     * @param key Set对应的键
     * @return Set集合
     */
    public Set<String> getSet(String key) {
        if (jedis.exists(key)) {
            Set<String> strings = jedis.smembers(key);
            return strings;
        } else {
            return null;
        }
    }

    /**
     * 储存一个值
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, String value) {
        jedis.set(key, value);
    }

    public String get(String key) {
        if (jedis.exists(key)) {

            return jedis.get(key);
        } else
            return null;
    }

    /**
     * 删除一个元素，可以是值，也可以是集合
     *
     * @param key 键
     * @return 是否成功
     */
    public boolean remove(String key) {
        if (jedis.exists(key)) {
            jedis.del(key);
            return true;
        }
        return false;
    }

    /**
     * 删除集合中的某个元素
     *
     * @param key   键
     * @param value 集合中元素的值
     * @return 是否成功
     */
    public boolean remove(String key, String value) {
        if (jedis.exists(key)) {
            jedis.srem(key, value);
            return true;
        }
        return false;
    }

    /**
     * 查询Redis中是否包含对应的键值
     *
     * @param key 键
     * @return 值
     */
    public boolean isExists(String key) {
        return jedis.exists(key);
    }

}
