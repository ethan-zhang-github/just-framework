package priv.just.framework.demo.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-29 17:15
 */
@Slf4j
public class ZkLock implements Lock {

    private String basePath;

    private CuratorFramework client;

    private AtomicInteger reentrant = new AtomicInteger(0);

    private Thread curThread;

    @Override
    public boolean lock() {
        synchronized (this) {
            if (reentrant.get() == 0) {
                curThread = Thread.currentThread();
                reentrant.incrementAndGet();
            } else {
                if (curThread.equals(Thread.currentThread())) {
                    return false;
                }
                reentrant.incrementAndGet();
                return true;
            }
        }
        try {
            boolean locked = tryLock();
            if (locked) {
                return true;
            }
            while (!locked) {
                await();
                List<String> waiters = getWaiters();
                if (checkLocked(waiters)) {
                    locked = true;
                }
            }
            return true;
        } catch (Exception e) {
            log.info("zk lock error", e);
            unlock();
        }
        return false;
    }

    @Override
    public boolean unlock() {
        return false;
    }

    private boolean tryLock() {
        // client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(basePath)
        return false;
    }

    private void await() {}
    
    private List<String> getWaiters() {
        return new LinkedList<>();
    }

    private boolean checkLocked(Collection<String> waiters) {
        return false;
    }

}
