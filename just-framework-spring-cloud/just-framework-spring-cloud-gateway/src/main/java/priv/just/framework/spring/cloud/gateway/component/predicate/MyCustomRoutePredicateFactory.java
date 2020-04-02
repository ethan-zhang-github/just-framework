package priv.just.framework.spring.cloud.gateway.component.predicate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-01 9:38
 */
@Component
public class MyCustomRoutePredicateFactory extends AbstractRoutePredicateFactory<MyCustomRoutePredicateFactory.Config> {

    public MyCustomRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
            return config.getAuthor().equalsIgnoreCase(queryParams.getFirst("author"));
        };
    }

    @Getter
    @Setter
    public static class Config {

        private String author;

    }

}
