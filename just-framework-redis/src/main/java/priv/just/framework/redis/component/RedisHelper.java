package priv.just.framework.redis.component;

import redis.clients.jedis.JedisPool;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-12-07 18:10
 */
public class RedisHelper {

    private JedisPool jedisPool;

    public RedisHelper(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

}
