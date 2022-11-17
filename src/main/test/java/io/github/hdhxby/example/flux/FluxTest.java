package io.github.hdhxby.example.flux;

import io.github.hdhxby.example.stream.StreamTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FluxTest {


    private static final Logger logger = LoggerFactory.getLogger(StreamTest.class);

    @Test
    public void testSubscriber() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        SubmissionPublisher publisher = new SubmissionPublisher();
        Flow.Subscriber subscriber = new Flow.Subscriber() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Object item) {
                System.out.println("onNext:" + item);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                subscription.cancel();
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
                countDownLatch.countDown();
            }
        };
        publisher.subscribe(subscriber);

        publisher.submit(1);
        publisher.submit(2);
        publisher.submit(3);
        publisher.submit(4);
        publisher.submit(5);
        publisher.submit(6);
        publisher.submit(7);
        publisher.submit(8);
        publisher.submit(9);

        publisher.close();

        countDownLatch.await();
    }


    @Test
    public void testProcessor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        SubmissionPublisher publisher = new SubmissionPublisher();
        Flow.Processor processor = new Flow.Processor() {
            private Flow.Subscriber subscriber;
            private Flow.Subscription subscription;
            @Override
            public void subscribe(Flow.Subscriber subscriber) {
                this.subscriber = subscriber;
                subscriber.onSubscribe(subscription);
            }

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscriber.onSubscribe(subscription);
            }

            @Override
            public void onNext(Object item) {
                System.out.println("Processor onNext:" + item);
                subscriber.onNext(item);
            }

            @Override
            public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onComplete() {
                System.out.println("Processor onComplete");
                subscriber.onComplete();
            }
        };

        Flow.Subscriber subscriber = new Flow.Subscriber() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                if(subscription != null) {
                    subscription.request(1);
                }
            }

            @Override
            public void onNext(Object item) {
                System.out.println("Subscriber onNext:" + item);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                subscription.cancel();
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Subscriber onComplete");
                countDownLatch.countDown();
            }
        };

        processor.subscribe(subscriber);

        publisher.subscribe(processor);

        publisher.submit(1);
        publisher.submit(2);
        publisher.submit(3);
        publisher.submit(4);
        publisher.submit(5);
        publisher.submit(6);
        publisher.submit(7);
        publisher.submit(8);
        publisher.submit(9);

        publisher.close();

        countDownLatch.await();
    }

}
