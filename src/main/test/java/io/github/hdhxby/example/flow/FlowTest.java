package io.github.hdhxby.example.flow;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlowTest {

    private static final Logger logger = LoggerFactory.getLogger(FlowTest.class);

    @Test
    public void test(){
        List<Integer> list = Stream.of(1,2,3,4,5,6,7,8,9).collect(Collectors.toList());
        Flux.fromIterable(list)
                .map(i -> i*2)
                .subscribe(System.out::println);
    }
}
