package priv.just.framework.security.repository;

import org.springframework.stereotype.Repository;
import priv.just.framework.security.domain.MyUserDetails;
import priv.just.framework.security.enums.UserRole;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static priv.just.framework.security.configuration.MyWebSecurityConfigurer.PASSWORD_ENCODER;

/**
 * 模拟用户持久层
 * @author Ethan Zhang
 */
@Repository
public class MyUserDetailsRepository {

    private final ConcurrentMap<String, MyUserDetails> myUserDetails = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        myUserDetails.put("admin", new MyUserDetails("admin", PASSWORD_ENCODER.encode("admin"), UserRole.ADMIN));
        myUserDetails.put("user", new MyUserDetails("user", PASSWORD_ENCODER.encode("user"), UserRole.USER));
        myUserDetails.put("subUser", new MyUserDetails("subUser", PASSWORD_ENCODER.encode("subUser"), UserRole.SUB_USER));
    }

    public MyUserDetails getByUsername(String username) {
        return myUserDetails.get(username);
    }

}
