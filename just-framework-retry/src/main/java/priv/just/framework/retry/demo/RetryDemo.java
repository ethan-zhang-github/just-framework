package priv.just.framework.retry.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import priv.just.framework.retry.exception.RetryException;

import java.util.Random;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-06 17:25
 */
@Slf4j
public class RetryDemo {

    private static Random random = new Random();

    public static void main(String[] args) {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(10));
        retryTemplate.setBackOffPolicy(new FixedBackOffPolicy());
        retryTemplate.execute(context -> {
            int retryCount = context.getRetryCount() + 1;
            log.info("开始第{}次重试...", retryCount);
            if (random.nextInt(10) < 1) {
                log.error("第{}次重试成功", retryCount);
                return "success";
            }
            log.error("第{}次重试失败", retryCount);
            throw new RetryException("fail");
        }, context -> {
            log.info("重试{}次均失败，默认返回", context.getRetryCount());
            return "success";
        });
    }

}
