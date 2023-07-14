package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService {
    @Value("${server.port}")
    private String port;

    private final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public void calculateWithStream() {
        int limit = 1_000_000;
        calculateWithStream(limit);
        calculateWithStreamParallel(limit);
        calculateWithLongStream(limit);
    }

    public void calculateWithStream(int limit) {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long end = System.currentTimeMillis();
        logger.info("Time1: " + (end - start));
    }

    public void calculateWithStreamParallel(int limit) {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long end = System.currentTimeMillis();
        logger.info("Time2: " + (end - start));
    }

    public void calculateWithLongStream(int limit) {
        long start = System.currentTimeMillis();
        long sum = LongStream
                .range(1, limit)
                .sum();
        long end = System.currentTimeMillis();
        logger.info("Time3: " + (end - start));
    }




}
