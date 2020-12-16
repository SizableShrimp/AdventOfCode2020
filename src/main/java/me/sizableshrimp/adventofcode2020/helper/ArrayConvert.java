/*
 * AdventOfCode2020
 * Copyright (C) 2020 SizableShrimp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.sizableshrimp.adventofcode2020.helper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayConvert {
    /**
     * Parse all lines to integers.
     * <b>NOTE: Not the same as {@link LineConvert#ints}</b>
     *
     * @return An array of parsed integers.
     */
    public static int[] ints(List<String> list) {
        return convert(list, Integer::valueOf).mapToInt(i -> i).toArray();
    }

    private static <T> Stream<T> convert(List<String> list, Function<String, T> func) {
        return list.stream()
                .map(func);
    }

    public static int[] unboxInts(List<Integer> boxed) {
        return boxed.stream().mapToInt(i -> i).toArray();
    }

    public static long[] longs(List<String> list) {
        return convert(list, Long::valueOf).mapToLong(l -> l).toArray();
    }

    public static long[] unboxLongs(List<Long> boxed) {
        return boxed.stream().mapToLong(l -> l).toArray();
    }

    public static double[] doubles(List<String> list) {
        return convert(list, Double::valueOf).mapToDouble(d -> d).toArray();
    }

    public static double[] unboxDoubles(List<Double> boxed) {
        return boxed.stream().mapToDouble(d -> d).toArray();
    }

    public static boolean[] booleans(List<String> list) {
        // No BooleanStream type :(
        List<Boolean> boxed = convert(list, Boolean::valueOf).collect(Collectors.toList());
        return unboxBooleans(boxed);
    }

    public static boolean[] unboxBooleans(List<Boolean> boxed) {
        boolean[] arr = new boolean[boxed.size()];
        for (int i = 0; i < boxed.size(); i++) {
            arr[i] = boxed.get(i);
        }
        return arr;
    }

    public static char[] chars(List<String> list) {
        // No CharStream type :(
        List<Character> boxed = list.stream()
                .flatMap(s -> LineConvert.chars(s).stream())
                .collect(Collectors.toList());
        return unboxChars(boxed);
    }

    public static char[] unboxChars(List<Character> boxed) {
        char[] arr = new char[boxed.size()];
        for (int i = 0; i < boxed.size(); i++) {
            arr[i] = boxed.get(i);
        }
        return arr;
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

    public static long[] prefixSumsLong(List<Long> list) {
        if (list.isEmpty())
            return new long[0];

        long[] prefixSums = new long[list.size()];
        prefixSums[0] = list.get(0);

        for (int i = 1; i < prefixSums.length; i++) {
            prefixSums[i] = list.get(i) + prefixSums[i - 1];
        }

        return prefixSums;
    }

    // private static <T, U extends BaseStream<T, U>> U convert(List<String> list, Function<String, T> func, Function<Stream<T>, U> transform) {
    //     Stream<T> boxedStream = list.stream()
    //             .map(func);
    //     return transform.apply(boxedStream);
    // }
}
