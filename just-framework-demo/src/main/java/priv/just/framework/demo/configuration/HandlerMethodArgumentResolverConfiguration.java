package priv.just.framework.demo.configuration;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class HandlerMethodArgumentResolverConfiguration {

    @Resource
    private RequestMappingHandlerAdapter RequestMappingHandlerAdapter;

    @Bean
    public SmartInitializingSingleton handlerMethodArgumentResolverInitializing() {
        return () -> {
            List<HandlerMethodArgumentResolver> resolvers = RequestMappingHandlerAdapter.getArgumentResolvers().stream()
                    .map(HandlerMethodArgumentResolverDecorator::new).collect(Collectors.toList());
            RequestMappingHandlerAdapter.setArgumentResolvers(resolvers);
        };
    }

}
