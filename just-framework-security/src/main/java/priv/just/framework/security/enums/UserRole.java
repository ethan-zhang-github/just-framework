package priv.just.framework.security.enums;

import lombok.Getter;

import java.util.EnumSet;

import static priv.just.framework.security.enums.UserAuthority.CREATE_ORDER;
import static priv.just.framework.security.enums.UserAuthority.QUERY_ORDER;

/**
 * 用户角色
 * @author Ethan Zhang
 */
@Getter
public enum UserRole {

    ADMIN(EnumSet.allOf(UserAuthority.class)),
    USER(EnumSet.of(QUERY_ORDER, CREATE_ORDER)),
    SUB_USER(EnumSet.of(QUERY_ORDER)),
    ;

    private final EnumSet<UserAuthority> userAuthorities;

    UserRole(EnumSet<UserAuthority> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

}
