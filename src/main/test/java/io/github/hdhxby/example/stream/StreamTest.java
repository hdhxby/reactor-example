package io.github.hdhxby.example.stream;

import io.github.hdhxby.example.flow.MonoTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    private static final Logger logger = LoggerFactory.getLogger(StreamTest.class);

    @Test
    public void test() {
        List<Integer> list = Stream
                .of(1,2,3,4,5,6,7,8,9)
                .collect(Collectors.toList());
        Integer sum = list.stream()
                .map(i -> i*2)
                .reduce(0,Integer::sum);
        System.out.println(sum);
    }
}
