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
