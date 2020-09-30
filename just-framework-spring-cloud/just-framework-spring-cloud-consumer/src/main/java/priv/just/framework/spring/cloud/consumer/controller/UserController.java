package priv.just.framework.spring.cloud.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
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
    @Resource
    private Source source;

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

    @PostMapping("addUserInfoAsync")
    public void addUserInfoAsync(@RequestBody UserInfo userInfo) {
        source.output().send(MessageBuilder.withPayload(userInfo).setHeader(RocketMQHeaders.TAGS, "add-user-tag").build());
    }

}
