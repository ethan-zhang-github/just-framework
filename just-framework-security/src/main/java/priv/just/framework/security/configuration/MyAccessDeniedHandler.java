package priv.just.framework.security.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import priv.just.framework.core.domain.vo.ResponseWrapper;
import priv.just.framework.security.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 已登录用户访问无权限资源处理
 * @author Ethan Zhang
 */
@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("用户无权访问该资源", accessDeniedException);
        ResponseUtil.writeAsJson(response, ResponseWrapper.unAuthorised());
    }

}
