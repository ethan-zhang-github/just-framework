package priv.just.framework.zookeeper.configuration;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.just.framework.zookeeper.configuration.property.ZookeeperProperties;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-11-18 11:32
 */
@Configuration
@EnableConfigurationProperties(ZookeeperProperties.class)
public class CuratorAutoConfiguration {

    @Autowired
    private ZookeeperProperties properties;

    @Bean
    public RetryPolicy defaultRetryPolicy() {
        return new ExponentialBackoffRetry(1000, 3);
    }

    @Bean
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(properties.getHost().concat(":")
                .concat(String.valueOf(properties.getPort())), defaultRetryPolicy());
        curatorFramework.start();
        return curatorFramework;
    }

}
