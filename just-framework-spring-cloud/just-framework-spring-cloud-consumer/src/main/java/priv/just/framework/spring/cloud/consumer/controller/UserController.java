package priv.just.framework.spring.cloud.consumer.controller;

import org.springframework.web.bind.annotation.*;
import priv.just.framework.spring.cloud.provider.api.domain.UserInfo;
import priv.just.framework.spring.cloud.provider.api.feign.UserClient;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserClient userClient;

    @GetMapping("getUserInfo")
    public UserInfo getUserInfo(@RequestParam("id") long id) {
        return userClient.getUserInfo(id);
    }

    @PostMapping("addUserInfo")
    public long addUserInfo(@RequestBody UserInfo userInfo) {
        return userClient.addUserInfo(userInfo);
    }

}
