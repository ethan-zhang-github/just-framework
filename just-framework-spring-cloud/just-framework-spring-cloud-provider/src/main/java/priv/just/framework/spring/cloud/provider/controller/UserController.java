package priv.just.framework.spring.cloud.provider.controller;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.*;
import priv.just.framework.spring.cloud.provider.api.domain.UserInfo;
import priv.just.framework.spring.cloud.provider.api.feign.UserClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("user")
public class UserController implements UserClient {

    private final Map<Long, UserInfo> cache = new ConcurrentHashMap<>();

    @GetMapping("getUserInfo")
    @Override
    public UserInfo getUserInfo(@RequestParam("id") long id) {
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

}
