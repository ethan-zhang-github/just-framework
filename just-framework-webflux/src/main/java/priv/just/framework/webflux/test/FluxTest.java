package priv.just.framework.webflux.test;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-27 17:23
 */
public class FluxTest {

    public static void main(String[] args) throws InterruptedException {
        /*Flux.create(fluxSink ->
                fluxSink.next(1).next(2).next(3).complete())
                .subscribe(System.out::println);*/

        /*Flux.generate(HashMap::new , (state, sink) -> {
            String key = UUID.randomUUID().toString();
            sink.next(key);
            state.put(key, 1);
            if (state.size() > 10) {
                sink.complete();
            }
            return state;
        }, System.out::println).subscribe(System.out::println);*/

        /*Flux.from(Mono.just("1")).subscribe(System.out::println);*/

        /*Flux.defer(() -> Mono.just(1)).subscribe(System.out::println);*/

        /*Flux.interval(Duration.ofSeconds(1)).subscribe(System.out::println);*/

        /*Flux.error(new RuntimeException("xxx")).onErrorReturn(1).subscribe(System.out::println);*/

        // Flux.error(new RuntimeException()).concatWith(Flux.just(1, 2, 3)).subscribe(System.out::println);

        /*Flux.fromStream(Stream.iterate(1, Math::incrementExact).limit(10));*/

        /*Flux.concat(Flux.error(new RuntimeException()), Flux.just(1, 2, 3)).onErrorReturn(4).subscribe(System.out::println);

        Flux.concatDelayError(Flux.error(new RuntimeException()), Flux.just(1, 2, 3)).onErrorReturn(4).subscribe(System.out::println);*/

        /*Flux.first(Flux.create(sink -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sink.next(1).complete();
        }), Flux.create(sink -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sink.next(2).complete();
        })).subscribe(System.out::println);*/

        /*Flux.mergeOrdered(Flux.interval(Duration.ofSeconds(1)), Flux.interval(Duration.ofSeconds(2))).subscribe(System.out::println);*/

        /*Flux.range(1, 10).subscribe(new MySubscriber());*/

        /*Flux.range(1, 100)
                .doOnRequest(num -> System.out.println("request:" + num))
                .subscribe(new BaseSubscriber<Integer>() {

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(10);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println(value);
                    }

        });*/

        /*Flux.generate(() -> 0, (state, sink) -> {
            sink.next(state);
            if (state == 10) {
                sink.complete();
            }
            return state + 1;
        }, state -> {
            System.out.println("state consumer : " + state);
        }).subscribe(System.out::println);*/

        Scheduler publishScheduler = Schedulers.newElastic("parallel-publish");
        Scheduler subscribeScheduler = Schedulers.newElastic("parallel-subscribe");

        Flux<Integer> flux = Flux.range(1, 3)
                .subscribeOn(subscribeScheduler)
                .map(i -> {
                    System.out.println("firstMap:" + Thread.currentThread().getName());
                    return i;
                })
                .publishOn(publishScheduler)
                .map(i -> {
                    System.out.println("secondMap:" + Thread.currentThread().getName());
                    return i;
                });

        Thread thread = new Thread(() -> {
            flux.subscribe(i -> {
                System.out.println("subscribe" + Thread.currentThread().getName());
            });
        });

        thread.start();
        thread.join();


        /*new CountDownLatch(1).await();*/
    }

}
