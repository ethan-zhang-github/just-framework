package priv.just.framework.demo.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-15 17:56
 */
@Slf4j
@RestController
@RequestMapping("cache")
public class CacheController {

    private Cache<Integer, String> cache;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder().initialCapacity(1 << 7).expireAfterWrite(1, TimeUnit.MINUTES)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
    }

    @RequestMapping("get")
    public String get(@RequestParam("key") Integer key) {
        String res = cache.getIfPresent(key);
        if (StringUtils.isBlank(res)) {
            if (key < 100) {
                res = UUID.randomUUID().toString();
                cache.put(key, res);
            }
        }
        return cache.getIfPresent(key);
    }

}
