package priv.just.framework.retry.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import priv.just.framework.retry.exception.RetryException;
import priv.just.framework.retry.service.RetryDemoService;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-01-06 9:02
 */
@Slf4j
@Service
public class RetryDemoServiceImpl implements RetryDemoService {

    private ThreadLocal<AtomicInteger> retryCount = new ThreadLocal<>();

    @Override
    @Retryable(value = RetryException.class, maxAttempts = 10, stateful = false,
            backoff = @Backoff())
    // @CircuitBreaker(value = RetryException.class)
    public void invoke(String src) {
        AtomicInteger atomicCount = retryCount.get();
        if (atomicCount == null) {
            retryCount.set(new AtomicInteger(0));
            atomicCount = retryCount.get();
        }
        int count = atomicCount.incrementAndGet();
        log.info("开始第{}次重试...", count);
        throw new RetryException(UUID.randomUUID().toString());
    }

    @Recover
    public void recover(RetryException ex, String src) {
        log.info("默认：" + src);
    }

}
