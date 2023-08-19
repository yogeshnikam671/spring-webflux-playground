package com.yogesh.springwebfluxplayground.mono;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

public class SampleMonoOperator implements Publisher<String> {

    @Override
    public void subscribe(Subscriber<? super String> s) {

    }
}
