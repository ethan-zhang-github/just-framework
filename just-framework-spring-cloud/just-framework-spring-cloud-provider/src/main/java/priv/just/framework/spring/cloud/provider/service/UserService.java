package priv.just.framework.spring.cloud.provider.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import priv.just.framework.spring.cloud.provider.api.domain.UserInfo;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class UserService {

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

    public UserInfo getUserInfo(long id) {
        Instant start = Instant.now();
        try {
            Thread.sleep(Duration.ofSeconds(random.nextInt(10)).toMillis());
        } catch (InterruptedException e) {
            log.error("", e);
        }
        log.info("getUserInfo cost {} seconds!", Duration.between(start, Instant.now()).getSeconds());
        return cache.get(id);
    }

    public long addUserInfo(UserInfo userInfo) {
        long id = RandomUtils.nextLong(1000000000L, 9999999999L);
        userInfo.setId(id);
        cache.put(id, userInfo);
        return id;
    }

}
