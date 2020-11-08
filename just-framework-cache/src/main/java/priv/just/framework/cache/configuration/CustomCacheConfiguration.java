package priv.just.framework.cache.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@EnableCaching
@Configuration
public class CustomCacheConfiguration extends CachingConfigurerSupport {

    @Autowired
    private CacheManager defaultCacheManager;

    @Override
    public CacheManager cacheManager() {
        return defaultCacheManager;
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

}
