package priv.just.framework.webmvc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import priv.just.framework.webmvc.actuator.endpoint.ThreadPoolEndpoint;
import priv.just.framework.webmvc.configuration.property.ThreadPoolProperties;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-12-10 17:41
 */
@Slf4j
@ConditionalOnProperty(prefix = ThreadPoolProperties.PREFIX, name = "enable", havingValue = "true")
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
@EnableAsync
public class ThreadPoolAutoConfiguration implements AsyncConfigurer {

    @Autowired
    private ThreadPoolProperties properties;

    private ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    @Override
    public Executor getAsyncExecutor() {
        return threadPoolTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncUncaughtExceptionHandler();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        executor.setThreadNamePrefix(properties.getThreadNamePrefix());
        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        try {
            executor.setRejectedExecutionHandler((RejectedExecutionHandler) Class.forName(properties.getRejectedExecutionHandler()).newInstance());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            log.error("【just framework webmvc】 ThreadPoolTaskExecutor init successful!", e);
        }
        log.info("【just framework webmvc】 ThreadPoolTaskExecutor init successful!");
        return executor;
    }

    @ConditionalOnMissingBean
    @Bean
    public AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
        return (Throwable ex, Method method, Object... params) -> {
            log.error("Async method 【{}】 throws exception 【{}】", method.getName(), ex.getMessage(), ex);
        };
    }

    @ConditionalOnClass(Health.class)
    @ConditionalOnAvailableEndpoint
    @Bean
    public ThreadPoolEndpoint threadPoolEndpoint() {
        return new ThreadPoolEndpoint();
    }

}
