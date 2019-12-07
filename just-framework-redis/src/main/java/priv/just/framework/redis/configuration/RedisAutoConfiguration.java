package priv.just.framework.redis.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.just.framework.redis.component.RedisHelper;
import priv.just.framework.redis.configuration.property.RedisProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-12-07 17:33
 */
@Slf4j
@ConditionalOnClass(Jedis.class)
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisHelper redisHelper() {
        RedisHelper redisHelper = new RedisHelper(jedisPool());
        log.info("【just framework redis】 RedisHelper init successful!");
        return redisHelper;
    }

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(new JedisPoolConfig(), redisProperties.getHost(), redisProperties.getPort(),
                redisProperties.getConnectionTimeout(), redisProperties.getPassword(), redisProperties.getDatabase());
    }

}
