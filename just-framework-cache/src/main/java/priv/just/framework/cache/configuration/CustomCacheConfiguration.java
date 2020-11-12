package priv.just.framework.cache.configuration;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Objects;

@EnableCaching
@Configuration
public class CustomCacheConfiguration extends CachingConfigurerSupport {

    @Autowired
    private CacheManager defaultCacheManager;

    @Override
    public CacheManager cacheManager() {
        return defaultCacheManager;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> getKeyPrefix(target, method) + SimpleKeyGenerator.generateKey(params);
    }

    private String getKeyPrefix(Object target, Method method) {
        Class<?> targetClazz = target.getClass();
        Method targetMethod = ReflectionUtils.findMethod(targetClazz, method.getName(), method.getParameterTypes());
        String prefix = "just-framework-cache:";
        if (Objects.isNull(targetMethod)) {
            return prefix;
        }
        Cacheable cacheable = AnnotationUtils.findAnnotation(targetMethod, Cacheable.class);
        if (Objects.nonNull(cacheable)) {
            return prefix + String.join("-", cacheable.cacheNames()) + ":";
        }
        CachePut cachePut = AnnotationUtils.findAnnotation(targetMethod, CachePut.class);
        if (Objects.nonNull(cachePut)) {
            return prefix + String.join("-", cachePut.cacheNames()) + ":";
        }
        cacheable = AnnotationUtils.findAnnotation(targetClazz, Cacheable.class);
        if (Objects.nonNull(cacheable)) {
            return prefix + String.join("-", cacheable.cacheNames()) + ":";
        }
        cachePut = AnnotationUtils.findAnnotation(targetClazz, CachePut.class);
        if (Objects.nonNull(cachePut)) {
            return prefix + String.join("-", cachePut.cacheNames()) + ":";
        }
        return prefix;
    }

    @Configuration
    static class CustomCaffeineCacheConfiguration {

        @ConditionalOnMissingBean(name = "defaultCacheManager")
        @Bean
        public CacheManager defaultCacheManager(Caffeine<Object, Object> defaultCaffeine) {
            CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
            caffeineCacheManager.setCaffeine(defaultCaffeine);
            return caffeineCacheManager;
        }

        @ConditionalOnMissingBean(name = "defaultCaffeine")
        @Bean
        public Caffeine<Object, Object> defaultCaffeine() {
            return Caffeine
                    .newBuilder()
                    .expireAfterWrite(Duration.ofDays(1))
                    .maximumSize(1 << 10);
        }

    }

    @EnableConfigurationProperties(CacheProperties.class)
    @Configuration
    static class CustomRedisCacheConfiguration {

        @Bean
        public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
                                              CacheProperties cacheProperties,
                                              ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration) {
            return RedisCacheManager.builder(redisConnectionFactory)
                    .cacheDefaults(redisCacheConfiguration.getIfAvailable(() -> createRedisCacheConfiguration(cacheProperties)))
                    .build();
        }

        private RedisCacheConfiguration createRedisCacheConfiguration(CacheProperties cacheProperties) {
            CacheProperties.Redis redisProperties = cacheProperties.getRedis();
            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
            config = config.serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));
            if (redisProperties.getTimeToLive() != null) {
                config = config.entryTtl(redisProperties.getTimeToLive());
            }
            if (redisProperties.getKeyPrefix() != null) {
                config = config.prefixKeysWith(redisProperties.getKeyPrefix());
            }
            if (!redisProperties.isCacheNullValues()) {
                config = config.disableCachingNullValues();
            }
            if (!redisProperties.isUseKeyPrefix()) {
                config = config.disableKeyPrefix();
            }
            return config;
        }

    }

}
