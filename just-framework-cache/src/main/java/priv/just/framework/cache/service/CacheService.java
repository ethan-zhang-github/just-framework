package priv.just.framework.cache.service;

import priv.just.framework.cache.domain.CacheKey;
import priv.just.framework.cache.domain.CacheValue;

public interface CacheService {

    CacheValue get(CacheKey cacheKey);

    CacheValue load(CacheKey cacheKey);

    void delete(CacheKey cacheKey);

}
