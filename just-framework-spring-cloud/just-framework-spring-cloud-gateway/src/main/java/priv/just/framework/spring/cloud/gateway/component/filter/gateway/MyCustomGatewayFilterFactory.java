package priv.just.framework.spring.cloud.gateway.component.filter.gateway;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-01 9:48
 */
@Slf4j
@Component
public class MyCustomGatewayFilterFactory extends AbstractGatewayFilterFactory<MyCustomGatewayFilterFactory.Config> {

    public MyCustomGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("x", "y");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            Instant start = Instant.now();
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    log.info("请求：【{}】，耗时：【{}】ms，x：【{}】，y：【{}】", exchange.getRequest().getPath().toString(),
                            Duration.between(start, Instant.now()).toMillis(), config.getX(), config.getY())));
        };
    }

    @Getter
    @Setter
    public static class Config {

        private String x;

        private String y;

    }

}
