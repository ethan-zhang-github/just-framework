package priv.just.framework.demo.controller;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@RestController
@RequestMapping("redis")
public class RedisController {

    private final Random random = new Random();

    @Resource
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        RTopic topic = redissonClient.getTopic("just-framework-demo:topic");
        topic.addListener(CacheData.class, (charSequence, cacheData) -> {
            System.out.println("topic:" + charSequence);
            System.out.println(JSON.toJSONString(cacheData));
        });
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
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class CacheData implements Serializable {

        private long id;

        private String name;

    }

}
