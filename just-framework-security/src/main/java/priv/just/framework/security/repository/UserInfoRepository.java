package priv.just.framework.security.repository;

import org.springframework.stereotype.Repository;
import priv.just.framework.security.enums.Authority;
import priv.just.framework.security.user.UserInfo;

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

    @PostConstruct
    public void init() {
        userInfoMap.put("user", new UserInfo("user", "123456", Authority.USER.getCode()));
        userInfoMap.put("admin", new UserInfo("admin", "123456", Authority.ADMIN.getCode()));
    }

    public UserInfo getByUsername(String username) {
        return userInfoMap.get(username);
    }

}
