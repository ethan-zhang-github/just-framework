package priv.just.framework.webflux.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<String> future = executorService.submit(() -> {
            // ... do something
            Thread.sleep(1000);
            return "second";
        });
        System.out.println("first");
        System.out.println(future.get());
    }

}
