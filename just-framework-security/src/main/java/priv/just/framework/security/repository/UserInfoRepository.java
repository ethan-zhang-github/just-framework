package priv.just.framework.security.repository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import priv.just.framework.security.domain.UserInfo;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-16 14:07
 */
@Repository
public class UserInfoRepository {

    private Map<String, UserInfo> userInfoMap = new ConcurrentHashMap<>();

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        userInfoMap.put("just1984", new UserInfo("just1984", passwordEncoder.encode("123456"), "ACCOUNT,SUB_ACCOUNT"));
    }

    public UserInfo getByUsername(String username) {
        return userInfoMap.get(username);
    }

}
