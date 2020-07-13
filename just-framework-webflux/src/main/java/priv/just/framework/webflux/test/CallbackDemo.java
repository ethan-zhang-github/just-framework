package priv.just.framework.webflux.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

public class CallbackDemo {

    public static void main(String[] args) throws InterruptedException {
        invokeAsync(new MyConsumer());
        System.out.println("first");
        new CountDownLatch(1).await();
    }

    private static void invokeAsync(Consumer<String> consumer) {
        ForkJoinPool.commonPool().submit(() -> {
            // ... do something
            Thread.sleep(1000);
            String result = "second";
            consumer.accept(result);
            return result;
        });
    }

    public static class MyConsumer implements Consumer<String> {
        @Override
        public void accept(String s) {

        }
    }

}
