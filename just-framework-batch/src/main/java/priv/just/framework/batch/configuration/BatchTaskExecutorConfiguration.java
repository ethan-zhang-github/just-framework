package priv.just.framework.batch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchTaskExecutorConfiguration {

    @Bean
    public ThreadPoolTaskExecutor batchTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("BATCH-");
        executor.setBeanName("batchTaskExecutor");
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setQueueCapacity(200);
        executor.setMaxPoolSize(executor.getCorePoolSize() << 2);
        return executor;
    }


}
