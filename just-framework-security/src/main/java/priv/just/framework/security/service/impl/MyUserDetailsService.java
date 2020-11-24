package priv.just.framework.security.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import priv.just.framework.security.domain.MyUserDetails;
import priv.just.framework.security.repository.MyUserDetailsRepository;

import javax.annotation.Resource;

/**
 * 自定义用户信息服务
 * @author Ethan Zhang
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private MyUserDetailsRepository myUserDetailsRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return myUserDetailsRepository.getByUsername(username);
    }

}
