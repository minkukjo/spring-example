package com.example.example.utils;

import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    public static String make(int length) {
        return random.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
