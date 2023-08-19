package com.yogesh.springwebfluxplayground.mono;

import org.junit.jupiter.api.Test;

class SampleMonoTest {

    @Test
    public void testSubscribe() {
        SampleMono sampleMono = new SampleMono("Hello");
        sampleMono.subscribe(new SampleSubscriber());
    }

}