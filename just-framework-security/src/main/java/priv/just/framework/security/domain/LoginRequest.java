package priv.just.framework.security.domain;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;

}
