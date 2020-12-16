package me.sizableshrimp.adventofcode2020.helper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListConvert {
    /**
     * Parse all lines to integers.
     * <b>NOTE: Not the same as {@link LineConvert#ints}</b>
     *
     * @return A list of parsed integers.
     */
    public static List<Integer> ints(List<String> list) {
        return convert(list, Integer::valueOf);
    }

    private static <T> List<T> convert(List<String> list, Function<String, T> func) {
        return list.stream()
                .map(func)
                .collect(Collectors.toList());
    }

    public static List<Long> longs(List<String> list) {
        return convert(list, Long::valueOf);
    }

    public static List<Double> doubles(List<String> list) {
        return convert(list, Double::valueOf);
    }

    public static List<Boolean> booleans(List<String> list) {
        return convert(list, Boolean::valueOf);
    }

    public static List<Character> chars(List<String> list) {
        return list.stream()
                .flatMap(s -> LineConvert.chars(s).stream())
                .collect(Collectors.toList());
    }
}
