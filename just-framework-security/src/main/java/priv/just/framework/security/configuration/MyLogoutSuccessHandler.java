package priv.just.framework.security.configuration;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import priv.just.framework.core.domain.vo.ResponseWrapper;
import priv.just.framework.security.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("用户退出成功");
        ResponseUtil.writeAsJson(response, ResponseWrapper.success());
    }

}
