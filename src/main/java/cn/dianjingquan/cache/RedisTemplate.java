package cn.dianjingquan.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Created by tommy on 2016-10-31.
 * common
 * cn.dianjingquan.cache.
 */
public class RedisTemplate {
    private JedisPool jedisPool;

    public RedisTemplate(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }

    public <T> T execute(RedisCallback<T> callback){
        Jedis jedis = jedisPool.getResource();
        try {
            return callback.handle(jedis);
        }catch (JedisException e){
            throw new JedisException(e);
        }finally {
            jedis.close();
        }
    }
}
