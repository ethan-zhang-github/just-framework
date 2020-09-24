package priv.just.framework.spring.cloud.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;
import priv.just.framework.spring.cloud.provider.api.domain.UserInfo;
import priv.just.framework.spring.cloud.provider.api.feign.UserClient;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserClient userClient;
    @Resource
    private CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @GetMapping("getUserInfo")
    public UserInfo getUserInfo(@RequestParam("id") long id) {
        return circuitBreakerFactory.create("getUserInfo").run(() -> userClient.getUserInfo(id), throwable -> {
            log.error("getUserInfo timeout!", throwable);
            return new UserInfo();
        });
    }

    @PostMapping("addUserInfo")
    public long addUserInfo(@RequestBody UserInfo userInfo) {
        return userClient.addUserInfo(userInfo);
    }

}
