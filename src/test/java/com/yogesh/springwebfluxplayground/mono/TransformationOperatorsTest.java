package com.yogesh.springwebfluxplayground.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

}
