package priv.just.framework.webflux.test;

import org.apache.commons.lang3.RandomUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class ReactiveProgramingDemo {

    public static void main(String[] args) throws InterruptedException {
        ReactiveProgramingDemo demo = new ReactiveProgramingDemo();
        Instant start = Instant.now();
        demo.invokeReactive().collectSortedList(String::compareTo).subscribe(res -> {
            Instant end = Instant.now();
            System.out.println(Arrays.toString(res.toArray()));
            System.out.println("耗时：" + Duration.between(start, end).toMillis());
        });
        new CountDownLatch(1).await();
    }

    private ParallelFlux<String> invokeReactive() {
        return getUserIds()
                 .parallel(16)
                 .runOn(Schedulers.newParallel("myParallel", 16))
                 .flatMap(userId -> {
                     Mono<String> userRole = getUserRole(userId);
                     Mono<String> userCompany = getUserCompany(userId);
                     return userRole.zipWith(userCompany,  (role, company) -> userId + "-" + role + "-" + company);
                 });
    }

    private Flux<Long> getUserIds() {
        return Flux.interval(Duration.ofMillis(10)).take(10);
    }

    private Mono<String> getUserRole(Long userId) {
        return Mono.fromSupplier(() -> {
            sleep(100);
            return RandomUtils.nextBoolean() ? "worker" : RandomUtils.nextBoolean() ? "leader" : "admin";
        });
    }

    private Mono<String> getUserCompany(Long userId) {
        return Mono.fromSupplier(() -> {
            sleep(80);
            return RandomUtils.nextBoolean() ? "A公司" : RandomUtils.nextBoolean() ? "B公司" : "C公司";
        });
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
