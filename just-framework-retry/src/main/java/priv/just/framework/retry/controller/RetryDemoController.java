package priv.just.framework.retry.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.just.framework.retry.service.RetryDemoService;

import javax.annotation.Resource;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-06 8:57
 */
@RestController
@RequestMapping("retry")
public class RetryDemoController {

    @Resource
    private RetryDemoService retryDemoService;

    @RequestMapping("invoke")
    public void invoke() {
        retryDemoService.invoke("xxx");
    }

}
