/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.helper;

import java.util.Collection;
import java.util.stream.IntStream;

public class Streams {
    public static IntStream unboxInts(Collection<Integer> boxed) {
        return boxed.stream().mapToInt(i -> i);
    }

    public static IntStream ints(int... arr) {
        return IntStream.of(arr);
    }

    public static String unboxChars(IntStream charStream) {
        return charStream.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
