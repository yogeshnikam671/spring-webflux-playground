package com.yogesh.springwebfluxplayground.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

public class MonoJustTest {

    @Test
    public void testMonoJustBlock() {
        Mono<Integer> mono = Mono.just(10);
        int res = mono.block();
        System.out.println(res);
    }

    @Test
    public void testMonoJustSubscribe() {
        AtomicInteger res = new AtomicInteger();

        Mono<Integer> mono = Mono.just(10).doOnNext(i -> res.set(i));
        System.out.println(res.get());

        mono.subscribe();
    }
}
