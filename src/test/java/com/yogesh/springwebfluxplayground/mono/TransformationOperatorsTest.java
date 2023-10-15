package com.yogesh.springwebfluxplayground.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;

public class TransformationOperatorsTest {

    @Test
    void map() {
        Mono.just(5)
            .map(it -> Mono.just(it + " is the number."))
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void flatMap() {
        Mono.just(5)
            .flatMap(it -> Mono.just(it + " is the number."))
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void filter() {
        Mono.just(5)
            .filter(it -> it == 1)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void filterWhen() {
        Mono.just(5)
            .filterWhen(it -> Mono.just(it == 1))
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void switchIfEmpty() {
        Mono.just(5)
            .filterWhen(it -> Mono.just(it == 1))
            .switchIfEmpty(Mono.just(0))
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void defaultIfEmpty() {
        Mono.just("")
            .map(it -> {
                    if (it == "") return "empty";
                    return it;
                }
            )
            .subscribe(it -> System.out.println("onNext --> " + it));

        Mono.just("something")
            .filter(it -> it != "")
            .defaultIfEmpty("empty")
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    // Flux specific
    @Test
    void flatMapMany() {
        Mono.just(5)
            .flatMapMany(it -> Flux.range(1, it))
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void collectList() {
        Flux.range(1, 5)
            .collectList()
            .map(it -> it.toString())
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void collectMap() {
        Flux.range(1, 5)
            .collectMap(
                it -> it + "_key",
                it -> it + "_value"
            )
            .map(it -> it.toString())
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void all() {
        Flux.range(1, 5)
            .all(it -> it > 0)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void any() {
        Flux.range(1, 5)
            .any(it -> it > 0)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void reduce() {
        Flux.range(1, 5)
            .reduce((x, y) -> x + y)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    // reduce function but also returns intermediate accumulated values as well.
    @Test
    void scan() {
        Flux.range(1, 5)
            .scan((x, y) -> x + y)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    // returns Mono void after the last element of the source is emitted.
    @Test
    void then() {
        Flux.range(1, 5)
            .then()
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    // returns Flux after the last element of the source is emitted.
    @Test
    void thenMany() {
        Flux.range(1, 5)
            .thenMany(Flux.range(6, 5))
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void hasElements() {
        Flux.range(1, 5)
            .hasElements()
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void hasElement() {
        Flux.range(1, 5)
            .hasElement(5)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void ignoreElements() {
        Flux.range(1, 5)
            .ignoreElements()
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err),
                () -> System.out.println("onComplete")
            );
    }

    @Test
    void count() {
        Flux.range(1, 5)
            .count()
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void repeat() {
        Flux.range(1, 5)
            .repeat(2)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void repeatWhen() {
        Flux.range(1, 5)
            .repeatWhen(it ->
                it.delayElements(Duration.ofSeconds(2)).take(2)
            ).doOnNext(
                it -> System.out.println("onNext --> " + it)
            ).blockLast();
    }

    @Test
    void distinct() {
        Flux.just(1, 1, 2, 3, 2, 4, 4, 5)
            .distinct()
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    // filters only consecutive duplicates.
    @Test
    void distinctUntilChanged() {
        Flux.just(1, 1, 2, 3, 2, 4, 4, 5)
            .distinctUntilChanged()
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void index() {
        Flux.range(1, 5)
            .index()
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err)
            );
    }

    @Test
    void last() {
        Flux.range(1, 5)
            .last()
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void elementAt() {
        Flux.range(1, 5)
            .elementAt(3)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void flatMapSequential() {
        Flux.range(1, 5)
            .flatMapSequential(it -> Flux.just(it + 1, it + 2))
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    /////
    // combines the elements of two sources into a Flux of tuples.
    @Test
    void zip() {
        Flux<Integer> flux1 = Flux.range(1, 5);
        Flux<Integer> flux2 = Flux.range(6, 5);
        Flux.zip(flux1, flux2)
            .subscribe(it ->
                System.out.println("flux 1 o/p : " + it.getT1() + " flux 2 o/p : " + it.getT2())
            );
    }

    // same as that of zip but with a combinator function whose return value is treated as a flux output
    // instead of a tuple.
    // zipWith can combine at most 2 sources.
    // zip can combine more than 2 sources.
    @Test
    void zipWith() {
        Flux<Integer> flux1 = Flux.range(1, 5);
        Flux<Integer> flux2 = Flux.range(6, 5);
        flux1.zipWith(
                flux2,
                (f1, f2) -> {
                    System.out.println("flux 1 o/p : " + f1 + " flux 2 o/p : " + f2);
                    return f1 + f2;
                }
            )
            .subscribe(it ->
                System.out.println("onNext --> " + it)
            );
    }

    @Test
    void retry() {
        Flux.range(1, 5)
            .map(it -> {
                if (it == 3) throw new RuntimeException("error");
                return it;
            })
            .retry(2)
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err)
            );
    }

    @Test
    void retryWhen() {
        Flux.range(1, 5)
            .map(it -> {
                if (it == 3) throw new RuntimeException("error");
                return it;
            })
            .retryWhen(Retry.from(it -> it.take(2)))
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err)
            );
    }

    @Test
    void timeout() {
        Flux.range(1, 5)
            .delayElements(java.time.Duration.ofSeconds(1))
            .timeout(java.time.Duration.ofSeconds(2))
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err)
            );
    }

    @Test
    void timeoutWhen() {
        Flux.range(1, 5)
            .delayElements(java.time.Duration.ofSeconds(1))
            .timeout(java.time.Duration.ofSeconds(2))
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err)
            );
    }

    @Test
    void timestamp() {
        Flux.range(1, 5)
            .timestamp()
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err)
            );
    }

    @Test
    void cache() {
        Flux.range(1, 5)
            .cache()
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err)
            );
    }

    @Test
    void cacheWithCapacity() {
        Flux.range(1, 5)
            .cache(2)
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err)
            );
    }

    @Test
    void cacheWithCapacityAndTtl() {
        Flux.range(1, 5)
            .cache(2, java.time.Duration.ofSeconds(2))
            .subscribe(
                it -> System.out.println("onNext --> " + it),
                err -> System.out.println("onError --> " + err)
            );
    }

    @Test
    void take() {
        Flux.range(1, 5)
            .take(2)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void takeLast() {
        Flux.range(1, 5)
            .takeLast(2)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void takeUntil() {
        Flux.range(1, 5)
            .takeUntil(it -> it == 3)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void takeWhile() {
        Flux.range(1, 5)
            .takeWhile(it -> it < 3)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    @Test
    void takeUntilOtherPredicate() {
        Flux.range(1, 5)
            .takeUntilOther(
                Flux.just(1,2,3)
                    .filter(it -> it > 2)
            )
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    // ???
    @Test
    void checkpoint() {
        Flux.range(1, 5)
            .checkpoint("checkpoint")
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    // ???
    @Test
    void groupBy() {
        Flux.range(1, 5)
            .groupBy(it -> it % 2 == 0)
            .subscribe(it -> System.out.println("onNext --> " + it));
    }

    // ??? - diagnostic purposes - figure out.
    @Test
    void hide() {
        Flux.range(1, 5)
            .hide()
            .subscribe(it -> System.out.println("onNext --> " + it));
    }
}




















