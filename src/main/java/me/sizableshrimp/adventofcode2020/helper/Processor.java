package me.sizableshrimp.adventofcode2020.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Processor {
    public static <T> BinaryOperator<Set<T>> unionBinaryOp() {
        return Processor::union;
    }

    // Union
    public static <T> Set<T> union(Set<T> result, Collection<T> element) {
        if (result == null) {
            result = new HashSet<>(element);
        } else {
            result.addAll(element);
        }
        return result;
    }

    public static <T> Set<T> unionArray(Set<T> result, T[] element) {
        return union(result, Arrays.asList(element));
    }

    public static <T> Set<T> intersectionArray(Set<T> result, T[] element) {
        return intersection(result, Arrays.asList(element));
    }

    // Intersection
    public static <T> Set<T> intersection(Set<T> result, Collection<T> element) {
        if (result == null) {
            result = new HashSet<>(element);
        } else {
            result.retainAll(element);
        }
        return result;
    }

    public static <T> BinaryOperator<Set<T>> intersectionBinaryOp() {
        return Processor::intersection;
    }

    // Union Array Overloads
    public static Set<Character> unionArray(Set<Character> result, char[] element) {
        List<Character> l = new ArrayList<>();
        for (char c : element) {
            l.add(c);
        }
        return union(result, l);
    }

    // Intersection Array Overloads
    public static Set<Character> intersectionArray(Set<Character> result, char[] element) {
        List<Character> l = new ArrayList<>();
        for (char c : element) {
            l.add(c);
        }
        return intersection(result, l);
    }

    // Splits
    public static <T> List<List<T>> split(List<T> list, Predicate<T> splitter) {
        int length = list.size();
        return split(length, i -> splitter.test(list.get(i)), list::subList)
                .collect(Collectors.toList());
    }

    private static <T> Stream<T> split(int length, IntPredicate splitter, BiFunction<Integer, Integer, T> func) {
        List<Integer> indices = new ArrayList<>();
        indices.add(-1);
        for (int i = 0; i < length; i++) {
            if (splitter.test(i))
                indices.add(i);
        }
        indices.add(length);

        return IntStream.range(0, indices.size() - 1)
                .mapToObj(i -> func.apply(indices.get(i) + 1, indices.get(i + 1)));
    }

    public static <T> Stream<Stream<T>> splitStream(List<T> list, Predicate<T> splitter) {
        int length = list.size();
        return split(length, i -> splitter.test(list.get(i)),
                (a, b) -> list.subList(a, b).stream());
    }

    public static <T> List<T[]> split(T[] arr, Predicate<T> splitter) {
        return split(arr.length, i -> splitter.test(arr[i]),
                (a, b) -> Arrays.copyOfRange(arr, a, b))
                .collect(Collectors.toList());
    }

    public static <T> Stream<Stream<T>> splitStream(T[] arr, Predicate<T> splitter) {
        return split(arr.length, i -> splitter.test(arr[i]),
                (a, b) -> Arrays.stream(Arrays.copyOfRange(arr, a, b)));
    }
}
