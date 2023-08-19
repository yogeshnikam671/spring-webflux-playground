package com.yogesh.springwebfluxplayground.mono;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SampleSubscription implements Subscription {
    private final Subscriber<String> subscriber;
    private final String value;

    public SampleSubscription(Subscriber<String> subscriber, String value) {
        this.subscriber = subscriber;
        this.value = value;
    }

    @Override
    public void request(long n) {
        subscriber.onNext(value);
        subscriber.onComplete();
    }

    @Override
    public void cancel() {
    }
}
