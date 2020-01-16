package priv.just.framework.security.enums;

import lombok.Getter;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-16 14:01
 */
@Getter
public enum Authority {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    Authority(String code) {
        this.code = code;
    }

    private String code;

}
