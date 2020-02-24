package priv.just.framework.retry.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.CircuitBreakerRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;
import priv.just.framework.retry.exception.BusException;

import java.util.Collections;
import java.util.Random;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-07 11:44
 */
@Slf4j
public class CircuitBreakerDemo {

    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new CircuitBreakerRetryPolicy(new SimpleRetryPolicy(10)));
        retryTemplate.setBackOffPolicy(new FixedBackOffPolicy());
        retryTemplate.execute(context -> {
            int retryCount = context.getRetryCount() + 1;
            log.info("开始第{}次重试...", retryCount);
            if (random.nextInt(10) < 1) {
                log.error("第{}次重试成功", retryCount);
                return "success";
            }
            log.error("第{}次重试失败", retryCount);
            throw new Exception("fail");
        }, context -> {
            log.info("重试{}次均失败，默认返回", context.getRetryCount());
            return "success";
        }, new DefaultRetryState("retry.state.key", new BinaryExceptionClassifier(Collections.singleton(BusException.class))));
    }

}
