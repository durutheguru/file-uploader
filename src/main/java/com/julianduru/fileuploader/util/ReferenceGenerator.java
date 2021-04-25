package com.julianduru.fileuploader.util;


import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;


@Component
public class ReferenceGenerator {

    private Faker faker = new Faker();

    private static final String DEFAULT_PREFIX = "file-";

    private AtomicLong counter = new AtomicLong(0);



    public String generate() {
        return generate(DEFAULT_PREFIX);
    }


    public String generate(String prefix) {
        return String.format(
            "%s%d-%d%d",
            prefix,
            System.currentTimeMillis(),
            counter.getAndIncrement() % 999_999,
            faker.random().nextLong(1_000_000)
        );
    }


}