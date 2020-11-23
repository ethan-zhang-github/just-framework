package priv.just.framework.security.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import priv.just.framework.security.enums.UserAuthority;

/**
 * 自定义用户权限
 * @author Ethan Zhang
 */
@Getter
public class MyUserAuthority implements GrantedAuthority {

    private final UserAuthority userAuthority;

    public MyUserAuthority(UserAuthority userAuthority) {
        this.userAuthority = userAuthority;
    }

    @Override
    public String getAuthority() {
        return userAuthority.toString();
    }

}
