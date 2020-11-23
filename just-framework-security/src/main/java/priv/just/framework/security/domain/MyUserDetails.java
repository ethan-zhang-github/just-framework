package priv.just.framework.security.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import priv.just.framework.security.enums.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义用户信息
 * @author Ethan Zhang
 */
@Data
public class MyUserDetails implements UserDetails {

    private long id;

    private boolean enable;

    private String username;

    private String password;

    private UserRole userRole;

    private List<MyUserAuthority> authorities;

    public MyUserDetails(String username, String password, UserRole userRole) {
        this.id = System.currentTimeMillis();
        this.enable = true;
        this.username = username;
        this.password = password;
        authorities = userRole.getUserAuthorities().stream().map(MyUserAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

}
