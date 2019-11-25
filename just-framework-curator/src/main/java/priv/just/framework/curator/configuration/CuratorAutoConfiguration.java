package priv.just.framework.curator.configuration;

import com.google.common.collect.Lists;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.just.framework.curator.configuration.property.CuratorProperties;
import priv.just.framework.curator.listener.LeaderSelectorListenerComposite;

import java.util.List;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-11-18 11:32
 */
@ConditionalOnProperty(prefix = CuratorProperties.PREFIX, name = "enable", value = "true", matchIfMissing = true)
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
        return CuratorFrameworkFactory.builder()
                .connectString(properties.getHost() + ":" + properties.getPort())
                .retryPolicy(defaultRetryPolicy()).build();
    }

    @ConditionalOnProperty(prefix = CuratorProperties.LeaderSelectorProperties.PREFIX, name = "enable", value = "true")
    @Configuration
    public static class LeaderSelectorAutoConfiguration {

        @Autowired(required = false)
        private List<LeaderSelectorListener> listeners = Lists.newArrayList();

        @ConditionalOnMissingBean
        @Bean
        public LeaderSelector leaderSelector(CuratorFramework curatorFramework, CuratorProperties properties) {
            LeaderSelectorListenerComposite listenerComposite = new LeaderSelectorListenerComposite(listeners);
            LeaderSelector leaderSelector = new LeaderSelector(curatorFramework, properties.getLeaderSelector().getPath(), listenerComposite);
            leaderSelector.start();
            return leaderSelector;
        }

    }

}
