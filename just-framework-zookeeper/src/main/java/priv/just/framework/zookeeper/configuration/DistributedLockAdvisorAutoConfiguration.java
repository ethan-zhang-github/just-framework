package priv.just.framework.zookeeper.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import priv.just.framework.zookeeper.aop.DistributedLockAdvice;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-11-18 20:36
 */
@Slf4j
@Configuration
public class DistributedLockAdvisorAutoConfiguration extends AspectJExpressionPointcutAdvisor {

    private static final String POINTCUT = "";

    @Autowired
    private CuratorFramework curatorFramework;

    @PostConstruct
    public void init() {
        this.setExpression(POINTCUT);
        this.setAdvice(new DistributedLockAdvice(curatorFramework));
        log.info("【just framework zookeeper】 DistributedLockAdvisorAutoConfiguration init successful!");
    }

}
