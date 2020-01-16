package priv.just.framework.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-16 10:49
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @GetMapping("hello")
    public String hello() {
        return UUID.randomUUID().toString();
    }

}
