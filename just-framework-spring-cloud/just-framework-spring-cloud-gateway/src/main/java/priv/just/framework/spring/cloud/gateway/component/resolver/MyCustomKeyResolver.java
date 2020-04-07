package priv.just.framework.spring.cloud.gateway.component.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-07 20:14
 */
@Component
public class MyCustomKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.fromSupplier(() -> (int) (Math.random() * 10)).map(String::valueOf);
    }

}
