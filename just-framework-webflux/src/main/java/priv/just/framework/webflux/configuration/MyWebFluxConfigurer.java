package priv.just.framework.webflux.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import priv.just.framework.webflux.handler.TestHandler;

import javax.annotation.Resource;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-31 17:20
 */
@Configuration
@EnableWebFlux
public class MyWebFluxConfigurer implements WebFluxConfigurer {

    @Resource
    private TestHandler testHandler;

    @Bean
    public RouterFunction testRouterFunction() {
        return RouterFunctions.route().path("/test", builder ->
                builder.GET("/hello", testHandler::hello))
                .build();
    }

}
