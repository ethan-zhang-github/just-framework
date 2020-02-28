package priv.just.framework.security.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-09-28 17:06
 */
@Slf4j
public class CookieAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("进入cookie授权校验");
        // Arrays.stream(request.getCookies()).forEach(cookie -> log.info(cookie.getName() + "：" + cookie.getValue()));
        filterChain.doFilter(request, response);
    }

}
