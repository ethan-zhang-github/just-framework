package priv.just.framework.zookeeper.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import priv.just.framework.zookeeper.annotation.DistributedLock;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-11-18 15:41
 */
@Slf4j
public class DistributedLockAdvice implements MethodInterceptor {

    private CuratorFramework curatorFramework;

    public DistributedLockAdvice(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        DistributedLock[] distributedLocks = invocation.getMethod().getDeclaredAnnotationsByType(DistributedLock.class);
        if (Objects.isNull(distributedLocks) || distributedLocks.length == 0) {
            throw new RuntimeException("DistributedLock annotation not found!");
        }
        DistributedLock distributedLock = distributedLocks[0];
        String lockPath = distributedLock.lockPath();
        Method targetMethod = invocation.getMethod();
        Class<?> targetClass = targetMethod.getDeclaringClass();
        if (StringUtils.isBlank(lockPath)) {
            lockPath = targetClass.getName().concat(".").concat(targetMethod.getName()).replaceAll("\\.", "/");
        }
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, lockPath);
        if (lock.acquire(distributedLock.time(), distributedLock.timeUnit())) {
            try {
                Object res = invocation.proceed();
                return res;
            } catch (Exception e) {
                log.error("invocation proceed error, class:{}, method:{}", targetClass.getName(), targetMethod.getName(), e);
            } finally {
                lock.release();
            }
        }
        throw new RuntimeException("acquire distributed lock error");
    }

}
