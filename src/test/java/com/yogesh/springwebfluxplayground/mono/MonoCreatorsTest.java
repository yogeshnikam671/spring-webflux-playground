package com.yogesh.springwebfluxplayground.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class MonoCreatorsTest {

    @Test
    void just() throws InterruptedException {
        Mono<Date> publisher = Mono.defer(() -> Mono.just(new Date()));

        Date res1 = publisher.block();
        System.out.println(res1);

        Thread.sleep(5000);

        Date res2 = publisher.block();
        System.out.println(res2);
    }

    @Test
    void deferContextual() {
        AtomicReference<String> res = new AtomicReference<>("");
        Mono<String> pub = Mono.just(5)
                            .flatMap(it ->
                                Mono.deferContextual(ctx ->
                                    Mono.just("The value is " + ctx.get("key"))
                                )
                            ).doOnNext(res::set)
                            .contextWrite(ctx -> ctx.put("key", "five"));
        pub.subscribe();

        System.out.println(res.get());
    }

    @Test
    void fromCallableTest() {
        Mono<Integer> pub = Mono.fromCallable(() -> 5);
        
        pub.subscribe((it) -> System.out.println(it));
    }

    @Test
    void fromSupplierTest() {
        Mono<Integer> pub = Mono.fromSupplier(() -> 5);
        pub.subscribe((it) -> System.out.println(it));
    }

    @Test
    void fromRunnableTest() {
        Mono<Integer> pub = Mono.fromRunnable(() -> System.out.println("Executed before completion"));
        pub.subscribe(
            (it) -> System.out.println(it),
            (err) -> System.out.println(err),
            () -> System.out.println("Completed")
        );
    }

    // converts any kind of publisher into a mono by taking into account
    // only the first emitted value
    @Test
    void fromTest() {
        Mono.from(Flux.just(1,2,3)).subscribe(
            (it) -> System.out.println(it),
            (err) -> System.out.println(err),
            () -> System.out.println("Completed")
        );
    }

    // Not a creation operator
    // When we want to wait for a number of publishers to finish
    // producing data, and then do something on completion of those;
    // we can use the `when` operator
    @Test
    void fromWhen() {
        Mono.when(
            Mono.just(1),
            Mono.just(2),
            Mono.error(new Exception(""))
        ).subscribe(
            (it) -> System.out.println(it),
            (err) -> System.out.println("error --> "+ err),
            () -> System.out.println("Completed")
        );
    }
}
