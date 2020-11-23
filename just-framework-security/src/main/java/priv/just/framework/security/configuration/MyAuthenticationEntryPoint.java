package priv.just.framework.security.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import priv.just.framework.core.domain.vo.ResponseWrapper;
import priv.just.framework.security.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录用户访问无权限资源处理
 * @author Ethan Zhang
 */
@Slf4j
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("未登录用户访问无权访问该资源", authException);
        ResponseUtil.writeAsJson(response, ResponseWrapper.unAuthenticated());
    }

}
