package priv.just.framework.redis.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import priv.just.framework.core.constant.GlobalConstant;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-12-07 17:33
 */
@Getter
@Setter
@ConfigurationProperties(prefix = RedisProperties.PREFIX)
public class RedisProperties {

    public static final String PREFIX = GlobalConstant.PREFIX + "." + "redis";

    private boolean enable = true;

    private String host = "localhost";

    private int port = 6379;

    private String password;

    private int connectionTimeout = 2000;

    private int database = 0;

}
