package priv.just.framework.spring.cloud.consumer;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients(basePackages = "priv.just.framework.spring.cloud.provider.api.feign")
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    public RequestInterceptor globalRequestInterceptor() {
        return template -> System.out.println("globalRequestInterceptor ...");
    }

}
