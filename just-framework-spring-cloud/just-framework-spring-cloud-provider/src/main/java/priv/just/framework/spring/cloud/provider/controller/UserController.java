package priv.just.framework.spring.cloud.provider.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;
import priv.just.framework.spring.cloud.provider.api.domain.UserInfo;
import priv.just.framework.spring.cloud.provider.api.feign.UserClient;
import priv.just.framework.spring.cloud.provider.service.UserService;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController implements UserClient {

    @Resource
    private UserService userService;

    @GetMapping("getUserInfo")
    @Override
    public UserInfo getUserInfo(@RequestParam("id") long id) {
        return userService.getUserInfo(id);
    }

    @PostMapping("addUserInfo")
    @Override
    public long addUserInfo(@RequestBody UserInfo userInfo) {
        return userService.addUserInfo(userInfo);
    }

    /*@StreamListener(value = Processor.INPUT)
    public void subscribe(Message<UserInfo> message) {
        UserInfo userInfo = message.getPayload();
        log.info("consume message {}", JSON.toJSONString(userInfo));
        addUserInfo(userInfo);
    }*/

}
