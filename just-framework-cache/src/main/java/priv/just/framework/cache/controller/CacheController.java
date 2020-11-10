package priv.just.framework.cache.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.just.framework.cache.domain.CacheKey;
import priv.just.framework.cache.domain.CacheValue;
import priv.just.framework.cache.service.CacheService;

import javax.annotation.Resource;

@RequestMapping("cache")
@RestController
public class CacheController {

    @Resource
    private CacheService cacheService;

    @PostMapping("get")
    public CacheValue get(@RequestBody CacheKey cacheKey) {
        return cacheService.get(cacheKey);
    }

    @PostMapping("getOther")
    public CacheValue getOther(@RequestBody CacheKey cacheKey) {
        return cacheService.getOther(cacheKey);
    }

    @PostMapping("load")
    public CacheValue load(@RequestBody CacheKey cacheKey) {
        return cacheService.load(cacheKey);
    }

    @PostMapping("delete")
    public void delete(@RequestBody CacheKey cacheKey) {
        cacheService.delete(cacheKey);
    }

}
