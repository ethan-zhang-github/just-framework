package priv.just.framework.demo.controller;

import org.springframework.web.bind.annotation.*;
import priv.just.framework.demo.annotation.ApiVersion;
import priv.just.framework.demo.vo.ReqVo;

@RestController
@RequestMapping("test")
public class TestController {

    @ApiVersion(100)
    @PostMapping("request")
    public void request(@RequestBody ReqVo reqVo) {
        System.out.println(reqVo.getName());
        System.out.println(reqVo.getVersion());
    }

}
