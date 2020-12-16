package me.sizableshrimp.adventofcode2020.helper;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Streams {
    public static IntStream unboxInts(Collection<Integer> boxed) {
        return boxed.stream().mapToInt(i -> i);
    }

    public static LongStream unboxLongs(Collection<Long> boxed) {
        return boxed.stream().mapToLong(l -> l);
    }

    public static String unboxChars(IntStream charStream) {
        return charStream.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public static Stream<Character> toCharBoxedStream(String str) {
        return str.chars().mapToObj(i -> (char) i);
    }
}
