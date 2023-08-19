package com.yogesh.springwebfluxplayground.mono;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

class SampleMonoTest {

    @Test
    public void testSubscribe() {
        SampleMono sampleMono = new SampleMono("Hello");
        sampleMono.subscribe(new SampleSubscriber());
    }

    @Test
    void testDoOnNext() {
        AtomicReference<String> res = new AtomicReference<>("");
        SampleMono sampleMono = new SampleMono("Hello");

        System.out.println("res before subscribe --> " + res);
        sampleMono.doOnNext(e -> res.set(e))
                .subscribe(new SampleSubscriber());

        System.out.println("res after subscribe --> " + res);
    }
}