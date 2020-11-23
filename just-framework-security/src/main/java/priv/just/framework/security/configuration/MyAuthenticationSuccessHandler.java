package priv.just.framework.security.configuration;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import priv.just.framework.core.domain.vo.ResponseWrapper;
import priv.just.framework.security.domain.LoginResponse;
import priv.just.framework.security.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录授权成功处理
 * @author Ethan Zhang
 */
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("用户登录授权成功:{}", JSON.toJSONString(authentication));
        ResponseUtil.writeAsJson(response, ResponseWrapper.success(new LoginResponse()));
    }

}
