package cn.dianjingquan.cache;

import redis.clients.jedis.Jedis;

/**
 * Created by tommy on 2016-10-31.
 * usercenter
 * cn.dianjingquan.cache.
 */
public interface RedisCallback<T> {
    T handle(Jedis jedis);
}
