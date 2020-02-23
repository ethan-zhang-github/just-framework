package priv.just.framework.security.component;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-09-28 17:06
 */
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(JSONObject.toJSONString(authentication));
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        log.info(JSONObject.toJSONString(authentication1));
        log.info("登录成功");
    }

}
