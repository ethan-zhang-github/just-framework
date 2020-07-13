package priv.just.framework.webflux.test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.tools.agent.ReactorDebugAgent;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-27 17:23
 */
@Slf4j
public class FluxTest {

    public static void main(String[] args) {
        ReactorDebugAgent.init();
        ReactorDebugAgent.processExistingClasses();

        /*Flux.range(1, 100)
                .map(i -> i * 2)
                .filter(i -> i % 2 == 0)
                .take(10)
                .publishOn(Schedulers.elastic())
                .subscribe(System.out::println);*/

        /*Flux.interval(Duration.ofSeconds(1)).buffer(10)*/

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

        /*Scheduler publishScheduler = Schedulers.newElastic("parallel-publish");
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
        thread.join();*/

        /*new CountDownLatch(1).await();*/

        /*Mono.fromCallable(() -> {
            System.out.println(Thread.currentThread().getName());
            return "sss";
        }).subscribe(System.out::println);*/

        /*Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "";
        })).subscribe(System.out::println);*/

        /*Mono.fromCallable(() -> {
            System.out.println(1);
            return 2;
        }).subscribe(i -> {
            System.out.println(i);
        });*/

        /*Flux.range(1, 10).flatMap(Mono::just).subscribe(System.out::println);*/

        /*Flux.mergeOrdered(Flux.interval(Duration.ofSeconds(1)), Flux.interval(Duration.ofSeconds(2))).subscribe(System.out::println);*/

        /*Flux.defer(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Flux.range(1, 10);
        });*/

        /*Flux.range(1, 10).doOnNext(System.out::println).last().subscribe(System.out::println);

        System.out.println(Thread.currentThread().getName());*/

        /*Flux.range(1, 100)
                .bufferUntil(i -> (i & 1) == 1)
                .map(l -> l.stream().map(String::valueOf).collect(Collectors.joining(", ")))
                .subscribe(log::info);*/

        /*Flux.<String>create(sink -> {
            sink.next(Thread.currentThread().getName());
            sink.complete();
        })
                .publishOn(Schedulers.single())
                .map(i -> String.format("[%s] %s", Thread.currentThread().getName(), i))
                .publishOn(Schedulers.elastic())
                .map(i -> String.format("[%s] %s", Thread.currentThread().getName(), i))
                .subscribeOn(Schedulers.parallel())
                .subscribe(log::info);

        new CountDownLatch(1).await();*/

        // System.out.println(System.currentTimeMillis());
        /*Instant start = Instant.now();

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            sleep(5000);
            System.out.println(0);
            return 0;
        })
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> {
                    sleep(2000);
                    System.out.println(1);
                    return 1;
                }), Integer::sum)
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> {
                    sleep(1000);
                    System.out.println(2);
                    return 1;
                }), Integer::sum)
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> {
                    sleep(6000);
                    System.out.println(3);
                    return 1;
                }), Integer::sum);

        try {
            System.out.println(future.get());
            Instant end = Instant.now();
            System.out.println(Duration.between(start, end).toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(e.getCause().getClass());
        }
        sleep(5000);*/
        /*long l = 1234597765123L;
        System.out.println(l / 1000);
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(l));*/

        /*CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            throw new IllegalStateException("xxx");
        }).exceptionally(e -> {
            System.out.println(e.getCause().getMessage());
            return null;
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/
        System.out.println(System.currentTimeMillis());
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
