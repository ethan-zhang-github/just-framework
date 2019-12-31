package priv.just.framework.demo.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-12-31 17:37
 */
@Slf4j
@RestController
@RequestMapping("demo")
public class DemoController {

    @PostMapping("post")
    public void post(@RequestBody @Validated PostData postData) {
        log.info(JSONObject.toJSONString(postData));
    }

    @Data
    public static class PostData {

        @NotNull
        private Integer id;

        @NotEmpty
        private String name;

    }

}
