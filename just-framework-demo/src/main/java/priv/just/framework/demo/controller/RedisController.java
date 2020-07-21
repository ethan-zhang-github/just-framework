package priv.just.framework.demo.controller;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("redis")
public class RedisController {

    private final Random random = new Random();

    @Resource
    private RedissonClient redissonClient;

    private RSemaphore semaphore;

    @PostConstruct
    public void init() {
        RTopic topic = redissonClient.getTopic("just-framework-demo:topic");
        topic.addListener(CacheData.class, (charSequence, cacheData) -> {
            System.out.println("topic:" + charSequence);
            System.out.println(JSON.toJSONString(cacheData));
        });

        semaphore = redissonClient.getSemaphore("just-framework-demo:semaphore");
        semaphore.trySetPermits(5);
    }

    @PostMapping("acquire")
    public void acquire() throws InterruptedException {
        if (semaphore.tryAcquire(10, TimeUnit.SECONDS)) {
            System.out.println("semaphore acquire successfully");
        }
    }

    @PostMapping("release")
    public void release() {
        semaphore.release();
    }

    @RequestMapping("test")
    public void test() {
        RBucket<CacheData> cacheDataRBucket = redissonClient.getBucket("just-framework-demo:object");
        cacheDataRBucket.set(new CacheData(random.nextLong(), "just"));
        CacheData cacheData = cacheDataRBucket.get();
        System.out.println(JSON.toJSONString(cacheData));

        RBinaryStream binaryStream = redissonClient.getBinaryStream("just-framework-demo:binary-stream");
        binaryStream.set("just".getBytes(StandardCharsets.UTF_8));
        byte[] bytes = binaryStream.get();
        System.out.println(new String(bytes, StandardCharsets.UTF_8));

        RAtomicLong atomicLong = redissonClient.getAtomicLong("just-framework-demo:atomic-long");
        atomicLong.set(random.nextLong());
        atomicLong.incrementAndGet();
        System.out.println(atomicLong.get());

        RTopic topic = redissonClient.getTopic("just-framework-demo:topic");
        topic.publish(new CacheData(random.nextLong(), "just"));

        RRateLimiter rateLimiter = redissonClient.getRateLimiter("just-framework-demo:rate-limiter");
        rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);
        rateLimiter.acquire();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class CacheData implements Serializable {

        private long id;

        private String name;

    }

}
