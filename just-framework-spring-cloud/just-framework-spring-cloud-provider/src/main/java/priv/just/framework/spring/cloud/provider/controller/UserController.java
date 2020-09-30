package priv.just.framework.spring.cloud.provider.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;
import priv.just.framework.spring.cloud.provider.api.channel.TestChannel;
import priv.just.framework.spring.cloud.provider.api.domain.UserInfo;
import priv.just.framework.spring.cloud.provider.api.feign.UserClient;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController implements UserClient {

    private final Map<Long, UserInfo> cache = new ConcurrentHashMap<>();

    private final Random random = new Random();

    @PostConstruct
    public void init() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(0L);
        userInfo.setAge(28);
        userInfo.setName("Ethan");
        cache.put(0L, userInfo);
    }

    @GetMapping("getUserInfo")
    @Override
    public UserInfo getUserInfo(@RequestParam("id") long id) {
        Instant start = Instant.now();
        try {
            Thread.sleep(Duration.ofSeconds(random.nextInt(10)).toMillis());
        } catch (InterruptedException e) {
            log.error("", e);
        }
        log.info("getUserInfo cost {} seconds!", Duration.between(start, Instant.now()).getSeconds());
        return cache.get(id);
    }

    @PostMapping("addUserInfo")
    @Override
    public long addUserInfo(@RequestBody UserInfo userInfo) {
        long id = RandomUtils.nextLong(1000000000L, 9999999999L);
        userInfo.setId(id);
        cache.put(id, userInfo);
        return id;
    }

    @StreamListener(value = Processor.INPUT)
    public void subscribe(Message<UserInfo> message) {
        UserInfo userInfo = message.getPayload();
        log.info("consume message {}", JSON.toJSONString(userInfo));
        addUserInfo(userInfo);
    }

}
