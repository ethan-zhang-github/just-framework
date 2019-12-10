package priv.just.framework.webmvc.actuator.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import priv.just.framework.core.constant.GlobalConstant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-09-06 11:49
 */
@Endpoint(id = ThreadPoolEndpoint.ID)
public class ThreadPoolEndpoint {

    public static final String ID = GlobalConstant.PREFIX + "-thread-pool";

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @ReadOperation
    public Map<String, Object> invoke() {
        Map<String, Object> map = new LinkedHashMap<>(1 << 4);
        map.put("poolSize", threadPoolTaskExecutor.getPoolSize());
        map.put("corePoolSize", threadPoolTaskExecutor.getCorePoolSize());
        map.put("queueSize", threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size());
        map.put("maxPoolSize", threadPoolTaskExecutor.getMaxPoolSize());
        map.put("activeCount", threadPoolTaskExecutor.getActiveCount());
        map.put("completedTaskCount", threadPoolTaskExecutor.getThreadPoolExecutor().getCompletedTaskCount());
        map.put("taskCount", threadPoolTaskExecutor.getThreadPoolExecutor().getTaskCount());
        map.put("isTerminated", threadPoolTaskExecutor.getThreadPoolExecutor().isTerminated());
        return map;
    }

}
