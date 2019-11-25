package priv.just.framework.curator.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import priv.just.framework.core.constant.GlobalConstant;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-11-18 11:33
 */
@Getter
@Setter
@ConfigurationProperties(prefix = GlobalConstant.PREFIX + "." + CuratorProperties.PREFIX)
public class CuratorProperties {

    public static final String PREFIX = "curator";

    private boolean enable = true;

    private String host = "localhost";

    private int port = 2181;

}
