package priv.just.framework.security.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ResponseUtil {

    public static void writeAsJson(HttpServletResponse response, Object data) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().write(JSON.toJSONString(data));
        } catch (IOException e) {
            log.error("response writer error", e);
        }
    }

}
