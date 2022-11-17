package io.github.hdhxby.example.flow;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;

public class MonoTest {

    private static final Logger logger = LoggerFactory.getLogger(MonoTest.class);

    @Test
    public void test() throws InterruptedException {
        Mono<Integer> mono = Mono.just(1);
        // 异步
        mono.subscribe(integer -> logger.debug("async {}",integer));
        Thread.sleep(1000l);
        // 异步转同步
        logger.debug("sync {}",mono.block());
    }

}
