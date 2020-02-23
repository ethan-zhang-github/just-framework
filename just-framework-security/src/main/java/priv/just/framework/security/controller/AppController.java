package priv.just.framework.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.just.framework.security.vo.req.LoginReq;

import java.util.UUID;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-16 10:48
 */
@RestController
@RequestMapping("app")
public class AppController {

    @GetMapping("hello")
    public String hello() {
        return UUID.randomUUID().toString();
    }

    @PostMapping("login")
    public String login(LoginReq loginReq) {
        return "success";
    }

    @RequestMapping("needLogin")
    public String needLogin() {
        return "needLogin";
    }

}
