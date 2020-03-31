package priv.just.framework.webflux.handler;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import priv.just.framework.webflux.vo.TestVo;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-31 17:16
 */
@Component
public class TestHandler {

    @Nonnull
    public Mono<ServerResponse> hello(ServerRequest request) {
        TestVo testVo = new TestVo();
        testVo.setAge((int) (Math.random() * 100));
        testVo.setName(RandomStringUtils.randomAlphabetic(10));
        Map<String, String> attrs = new HashMap<>();
        attrs.put("key", UUID.randomUUID().toString());
        testVo.setAttrs(attrs);
        return ServerResponse.ok().bodyValue(testVo);
    }

}
