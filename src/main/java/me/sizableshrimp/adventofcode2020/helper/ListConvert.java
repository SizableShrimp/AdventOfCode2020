/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.helper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListConvert {
    /**
     * Parse all lines to integers.
     * <b>NOTE: Not the same as {@link LineConvert#ints}</b>
     * @return A list of parsed integers.
     */
    public static List<Integer> ints(List<String> list) {
        return convert(list, Integer::valueOf);
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

    public static boolean[][] copyOf(boolean[][] array) {
        boolean[][] copy = new boolean[array.length][];
        for (int i = 0; i < array.length; i++) {
            boolean[] arr = array[i];
            copy[i] = new boolean[arr.length];
            System.arraycopy(arr, 0, copy[i], 0, arr.length);
        }
        return copy;
    }

    public static int[][] copyOf(int[][] array) {
        int[][] copy = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            int[] arr = array[i];
            copy[i] = new int[arr.length];
            System.arraycopy(arr, 0, copy[i], 0, arr.length);
        }
        return copy;
    }

    private static <T> List<T> convert(List<String> list, Function<String, T> func) {
        return list.stream()
                .map(func)
                .collect(Collectors.toList());
    }
}
