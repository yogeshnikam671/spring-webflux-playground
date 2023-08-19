package com.yogesh.springwebfluxplayground.mono;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SampleSubscriber implements Subscriber<String> {
    @Override
    public void onSubscribe(Subscription s) {
       s.request(1);
    }

    @Override
    public void onNext(String s) {
        System.out.println("onNext --> " + s);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");
    }
}
