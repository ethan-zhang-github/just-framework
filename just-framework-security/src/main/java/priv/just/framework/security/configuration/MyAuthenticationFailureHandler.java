package priv.just.framework.security.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import priv.just.framework.core.domain.vo.ResponseWrapper;
import priv.just.framework.security.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录授权失败处理
 * @author Ethan Zhang
 */
@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.error("用户登录授权失败", exception);
        ResponseUtil.writeAsJson(response, ResponseWrapper.authenticationError());
    }

}
