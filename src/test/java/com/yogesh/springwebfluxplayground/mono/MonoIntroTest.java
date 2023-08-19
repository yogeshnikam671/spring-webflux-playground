package com.yogesh.springwebfluxplayground.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

public class MonoIntroTest {

  // hot publisher // evaluates just once
  @Test
  void shouldRun() throws InterruptedException {
    Mono mono = Mono.just(new Date());

    StepVerifier.create(mono).consumeNextWith(value -> {
      System.out.println("The value first --> " + value);
    }).verifyComplete();

    Thread.sleep(5000);

    StepVerifier.create(mono).consumeNextWith(value -> {
      System.out.println("The value second --> " + value);
    }).verifyComplete();
  }

  // cold publisher // evaluates a new value on every subscribe
  @Test
  void shouldRunDefer() throws InterruptedException {
    Mono mono = Mono.defer(() -> Mono.just(new Date()));

    StepVerifier.create(mono).consumeNextWith(value -> {
      System.out.println("The value first --> " + value);
    }).verifyComplete();

    Thread.sleep(5000);

    StepVerifier.create(mono).consumeNextWith(value -> {
      System.out.println("The value second --> " + value);
    }).verifyComplete();
  }
}
