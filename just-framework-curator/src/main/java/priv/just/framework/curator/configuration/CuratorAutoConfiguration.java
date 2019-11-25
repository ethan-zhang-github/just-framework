package priv.just.framework.curator.configuration;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.just.framework.curator.configuration.property.CuratorProperties;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-11-18 11:32
 */
@ConditionalOnProperty(prefix = "just.curator", name = "enable", value = "true", matchIfMissing = true)
@Configuration
@EnableConfigurationProperties(CuratorProperties.class)
public class CuratorAutoConfiguration {

    @Autowired
    private CuratorProperties properties;

    @ConditionalOnMissingBean
    @Bean
    public RetryPolicy defaultRetryPolicy() {
        return new ExponentialBackoffRetry(1000, 3);
    }

    @ConditionalOnMissingBean
    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(properties.getHost().concat(":")
                .concat(String.valueOf(properties.getPort())), defaultRetryPolicy());
        curatorFramework.start();
        return curatorFramework;
    }

}
