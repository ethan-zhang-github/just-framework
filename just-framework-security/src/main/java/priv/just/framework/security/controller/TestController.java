package priv.just.framework.security.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.just.framework.security.service.OrderService;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private OrderService orderService;

    @RequestMapping("printAllOrder")
    public void printAllOrder() {
        log.info(JSON.toJSONString(orderService.getAll()));
    }

}
