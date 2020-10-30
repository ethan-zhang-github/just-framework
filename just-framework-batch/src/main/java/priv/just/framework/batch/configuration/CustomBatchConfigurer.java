package priv.just.framework.batch.configuration;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class CustomBatchConfigurer implements BatchConfigurer {

    private MapJobRepositoryFactoryBean jobRepositoryFactory;

    private JobRepository jobRepository;

    @Override
    public JobRepository getJobRepository() throws Exception {
        jobRepositoryFactory = new MapJobRepositoryFactoryBean(getTransactionManager());
        jobRepositoryFactory.afterPropertiesSet();
        jobRepository = jobRepositoryFactory.getObject();
        return jobRepository;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
        jobExplorerFactory.afterPropertiesSet();
        return jobExplorerFactory.getObject();
    }

}
