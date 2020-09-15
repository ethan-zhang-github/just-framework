package priv.just.framework.spring.cloud.provider.api.feign;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import priv.just.framework.spring.cloud.provider.api.domain.UserInfo;

@FeignClient(name = "userClient", path = "user", configuration = UserClient.Configuration.class)
public interface UserClient {

    @GetMapping("getUserInfo")
    UserInfo getUserInfo(@RequestParam("id") long id);

    @PostMapping("addUserInfo")
    long addUserInfo(@RequestBody UserInfo userInfo);

    class Configuration {

        @Bean
        public RequestInterceptor userClientRequestInterceptor() {
            return template -> System.out.println("userClientRequestInterceptor ...");
        }

    }

}
