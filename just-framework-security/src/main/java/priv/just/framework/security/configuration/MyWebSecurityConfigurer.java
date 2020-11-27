package priv.just.framework.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import javax.annotation.Resource;

/**
 * spring security 核心配置
 * @author Ethan Zhang
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableRedisHttpSession(redisNamespace = "just:session:")
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    public static final String PERMIT_ALL_URL = "/test/**";
    public static final String LOGIN_URL = "/user/security/login";
    public static final String LOGOUT_URL = "/user/security/logout";

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private RedisIndexedSessionRepository redisIndexedSessionRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 表单方式登录
                .formLogin()
                // 登录处理地址
                .loginProcessingUrl(LOGIN_URL)
                // 登录成功处理
                .successHandler(myAuthenticationSuccessHandler())
                // 登录失败处理
                .failureHandler(myAuthenticationFailureHandler()).and()
                // 记住用户身份
                .rememberMe()
                // 集成 spring session
                .rememberMeServices(springSessionRememberMeServices()).and()
                // 用户退出
                .logout()
                // 用户退出地址
                .logoutUrl(LOGOUT_URL)
                // 用户退出成功处理
                .logoutSuccessHandler(myLogoutSuccessHandler()).and()
                // 授权请求
                .authorizeRequests()
                // 无授权地址
                .antMatchers(PERMIT_ALL_URL).permitAll()
                // 其余所有请求都需要授权访问
                .anyRequest().authenticated().and()
                // 异常处理
                .exceptionHandling()
                // 未登录用户访问无权限资源处理
                .authenticationEntryPoint(myAuthenticationEntryPoint())
                // 已登录用户访问无权限资源处理
                .accessDeniedHandler(myAccessDeniedHandler()).and()
                // 会话管理
                .sessionManagement()
                // 最多同时存在 session 数量
                .maximumSessions(3)
                // 集成 spring session
                .sessionRegistry(springSessionBackedSessionRegistry()).and().and()
                // 跨域处理
                .cors().and()
                // CSRF（跨站请求伪造）支持
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(PASSWORD_ENCODER);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler myAuthenticationFailureHandler() {
        return new MyAuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler myLogoutSuccessHandler() {
        return new MyLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationEntryPoint myAuthenticationEntryPoint() {
        return new MyAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler myAccessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Bean
    public RememberMeServices springSessionRememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setRememberMeParameterName("remember");
        return rememberMeServices;
    }

    @Bean
    public SpringSessionBackedSessionRegistry<?> springSessionBackedSessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(redisIndexedSessionRepository);
    }

}
