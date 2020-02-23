package priv.just.framework.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import priv.just.framework.security.domain.UserInfo;
import priv.just.framework.security.repository.UserInfoRepository;

import javax.annotation.Resource;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-16 13:54
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        return userInfoRepository.getByUsername(username);
    }

}
