package priv.just.framework.webflux.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.just.framework.webflux.vo.TestVo;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-21 18:06
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @PostMapping("/hello")
    public Mono<String> hello(@RequestBody TestVo testVo) {
        return Mono.just(testVo).map(JSONObject::toJSONString);
    }

}
