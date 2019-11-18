package priv.just.framework.zookeeper.configuration.property;

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
@ConfigurationProperties(prefix = GlobalConstant.PREFIX + "." + ZookeeperProperties.PREFIX)
public class ZookeeperProperties {

    public static final String PREFIX = "zk";

    private String host = "localhost";

    private int port = 2181;

}
