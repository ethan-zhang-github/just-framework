package priv.just.framework.redis.configuration;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.just.framework.redis.configuration.property.RedisProperties;
import reactor.core.publisher.Flux;

/**
 * Redisson相关配置
 * @see <a href="https://github.com/redisson/redisson">Redisson</a>
 */
@Slf4j
@ConditionalOnClass(RedissonClient.class)
@EnableConfigurationProperties(RedisProperties.class)
@Configuration
public class RedissonAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public Config redissonConfig() {
        Config config = new Config();
        config.useSingleServer().setAddress(String.format("redis://%s:%s", redisProperties.getHost(), redisProperties.getPort()))
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase())
                .setConnectTimeout(redisProperties.getConnectionTimeout())
                .setConnectionPoolSize(redisProperties.getConnectionPoolSize());
        return config;
    }

    @Bean
    public RedissonClient redissonClient() {
        log.info("【just framework redis】 RedissonClient init successful!");
        return Redisson.create(redissonConfig());
    }

    @ConditionalOnClass(Flux.class)
    @Bean
    public RedissonReactiveClient redissonReactiveClient() {
        log.info("【just framework redis】 RedissonReactiveClient init successful!");
        return Redisson.createReactive(redissonConfig());
    }

}
