/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.helper;

import java.util.List;
import java.util.stream.IntStream;

public class Streams {
    public static IntStream unboxIntList(List<Integer> boxedList) {
        return boxedList.stream().mapToInt(i -> i);
    }
}
