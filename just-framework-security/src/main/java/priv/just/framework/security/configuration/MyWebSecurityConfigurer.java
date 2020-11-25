package priv.just.framework.security.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * spring security 核心配置
 * @author Ethan Zhang
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    public static final String COOKIE_NAME = "just-token";
    public static final String COOKIE_DOMAIN = "localhost";
    public static final String PERMIT_ALL_URL = "/test/**";
    public static final String LOGIN_URL = "/user/security/login";
    public static final String LOGOUT_URL = "/user/security/logout";

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 表单方式登录
                .formLogin()
                // 登录处理地址
                .loginProcessingUrl(LOGIN_URL)
                // 登录成功处理
                .successHandler(new MyAuthenticationSuccessHandler())
                // 登录失败处理
                .failureHandler(new MyAuthenticationFailureHandler()).and()
                // 记住用户身份
                .rememberMe()
                // 总是记住
                .alwaysRemember(true)
                // token 有效时长
                .tokenValiditySeconds((int) Duration.ofDays(1).getSeconds())
                // cookie 域名
                .rememberMeCookieDomain(COOKIE_DOMAIN)
                // cookie 名称
                .rememberMeCookieName(COOKIE_NAME).and()
                // 用户退出
                .logout()
                // 用户退出地址
                .logoutUrl(LOGOUT_URL)
                // 用户退出成功处理
                .logoutSuccessHandler(new MyLogoutSuccessHandler()).and()
                // 授权请求
                .authorizeRequests()
                // 无授权地址
                .antMatchers(PERMIT_ALL_URL).permitAll()
                // 其余所有请求都需要授权访问
                .anyRequest().authenticated().and()
                // 异常处理
                .exceptionHandling()
                // 未登录用户访问无权限资源处理
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                // 已登录用户访问无权限资源处理
                .accessDeniedHandler(new MyAccessDeniedHandler()).and()
                // 会话管理
                .sessionManagement()
                // session 无状态模式
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 跨域处理
                .cors().and()
                // CSRF（跨站请求伪造）支持
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(PASSWORD_ENCODER);
    }

}
