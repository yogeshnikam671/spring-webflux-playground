package com.yogesh.springwebfluxplayground.mono;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class SampleSubscriber<String> extends BaseSubscriber<String> {
    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        request(1);
    }

    public void hookOnNext(String value) {
        System.out.println("onNext --> " + value);
    }
}
