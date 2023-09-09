package com.yogesh.springwebfluxplayground.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class FluxCreatorsTest {

    @Test
    void just() {
         Flux<Integer> pub = Flux.just(1, 2, 3, 4, 5);
         pub.subscribe(
             (it) -> System.out.println("onNext --> " + it),
             (err) -> System.out.println("onError --> " + err),
             () -> System.out.println("onComplete")
         );
    }

    @Test
    void defer() {
        Flux<Integer> pub = Flux.defer(() -> Flux.just(1, 2, 3, 4, 5));
        pub.subscribe(
            (it) -> System.out.println("onNext --> " + it),
            (err) -> System.out.println("onError --> " + err),
            () -> System.out.println("onComplete")
        );
    }

    @Test
    void fromIterable() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Flux<Integer> pub = Flux.fromIterable(list);
        pub.subscribe(
            (it) -> System.out.println("onNext --> " + it),
            (err) -> System.out.println("onError --> " + err),
            () -> System.out.println("onComplete")
        );
    }

    @Test
    void range() {
        Flux<Integer> pub = Flux.range(1, 5);
        pub.subscribe(
            (it) -> System.out.println("onNext --> " + it),
            (err) -> System.out.println("onError --> " + err),
            () -> System.out.println("onComplete")
        );
    }

    @Test
    void fromArray() {
        Integer[] arr = {1, 2, 3, 4, 5};
        Flux<Integer> pub = Flux.fromArray(arr);
        pub.subscribe(
            (it) -> System.out.println("onNext --> " + it),
            (err) -> System.out.println("onError --> " + err),
            () -> System.out.println("onComplete")
        );
    }

    @Test
    void fromStream() {
        Flux<Integer> pub = Flux.fromStream(Arrays.asList(1, 2, 3, 4, 5).stream());
        pub.subscribe(
            (it) -> System.out.println("onNext --> " + it),
            (err) -> System.out.println("onError --> " + err),
            () -> System.out.println("onComplete")
        );
    }

    @Test
    void from() {
        Flux<Integer> pub = Flux.from(Mono.just(1));
        pub.subscribe(
            (it) -> System.out.println("onNext --> " + it),
            (err) -> System.out.println("onError --> " + err),
            () -> System.out.println("onComplete")
        );
    }

    @Test
    void empty() {
        Flux<Integer> pub = Flux.empty();
        pub.subscribe(
            (it) -> System.out.println("onNext --> " + it),
            (err) -> System.out.println("onError --> " + err),
            () -> System.out.println("onComplete")
        );
    }
}
