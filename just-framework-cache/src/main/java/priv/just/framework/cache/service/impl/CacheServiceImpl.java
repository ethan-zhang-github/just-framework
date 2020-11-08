package priv.just.framework.cache.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import priv.just.framework.cache.domain.CacheKey;
import priv.just.framework.cache.domain.CacheValue;
import priv.just.framework.cache.service.CacheService;

@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Cacheable(cacheNames = "common")
    @Override
    public CacheValue get(CacheKey cacheKey) {
        sleep(1000);
        return new CacheValue();
    }

    @CachePut(cacheNames = "common")
    @Override
    public CacheValue load(CacheKey cacheKey) {
        sleep(1000);
        return new CacheValue();
    }

    @CacheEvict(cacheNames = "common")
    @Override
    public void delete(CacheKey cacheKey) {
        sleep(1000);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("sleep error", e);
        }
    }

}
