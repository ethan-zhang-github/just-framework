package priv.just.framework.curator.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import priv.just.framework.curator.aop.DistributedLockAdvice;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-11-18 20:36
 */
@Slf4j
@ConditionalOnBean(CuratorFramework.class)
@AutoConfigureAfter(CuratorAutoConfiguration.class)
@Configuration
public class DistributedLockAdvisorAutoConfiguration extends AspectJExpressionPointcutAdvisor {

    private static final String POINTCUT = "@annotation(priv.just.framework.curator.annotation.DistributedLock)";

    private CuratorFramework curatorFramework;

    @Autowired
    public DistributedLockAdvisorAutoConfiguration(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    @PostConstruct
    public void init() {
        this.setExpression(POINTCUT);
        this.setAdvice(new DistributedLockAdvice(curatorFramework));
        log.info("【just framework curator】 DistributedLockAdvisorAutoConfiguration init successful!");
    }

}
