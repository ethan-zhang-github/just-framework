package priv.just.framework.webmvc.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import priv.just.framework.core.constant.GlobalConstant;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-12-10 17:42
 */
@Getter
@Setter
@ConfigurationProperties(prefix = ThreadPoolProperties.PREFIX)
public class ThreadPoolProperties {

    public static final String PREFIX = GlobalConstant.PREFIX + ".thread-pool";

    private boolean enable = false;

    private int corePoolSize = Runtime.getRuntime().availableProcessors();

    private int maxPoolSize = Integer.MAX_VALUE;

    private int queueCapacity = Integer.MAX_VALUE;

    private int keepAliveSeconds = 60;

    private String rejectedExecutionHandler = "java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy";

    private String threadNamePrefix = "Just-ThreadPool-";

}
