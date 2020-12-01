/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.helper;

import java.util.List;
import java.util.stream.IntStream;

public class Streams {
    public static IntStream unboxInts(List<Integer> boxed) {
        return boxed.stream().mapToInt(i -> i);
    }

    public static IntStream ints(int... arr) {
        return IntStream.of(arr);
    }
}
