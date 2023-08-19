package com.yogesh.springwebfluxplayground.mono;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class SampleMono implements Publisher<String> {
    private final String data;

    public SampleMono(String data) {
        this.data = data;
    }

    @Override
    public void subscribe(Subscriber<? super String> s) {
        SampleSubscription sampleSubscription =
                new SampleSubscription((Subscriber<String>) s, data);
        s.onSubscribe(sampleSubscription);
    }
}
