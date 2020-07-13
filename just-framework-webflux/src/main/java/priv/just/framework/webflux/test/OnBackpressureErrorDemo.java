package priv.just.framework.webflux.test;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class OnBackpressureErrorDemo {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(1))
                .map(i -> ByteBuffer.allocate(1024 * 1024))
                //.onBackpressureBuffer(Integer.MAX_VALUE)
                //.onBackpressureError()
                //.onBackpressureDrop()
                .onBackpressureLatest()
                .subscribe(new BaseSubscriber<ByteBuffer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(10);
                    }

                    @Override
                    protected void hookOnNext(ByteBuffer buffer) {
                        executor.execute(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("next: " + buffer);
                            request(1);
                        });
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("complete");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        new CountDownLatch(1).await();
    }

}
