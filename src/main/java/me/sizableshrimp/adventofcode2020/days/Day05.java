/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.Streams;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.stream.Collectors;

public class Day05 extends Day {
    @Override
    protected Result evaluate() {
        Set<Integer> seats = lines.stream().map(s -> Integer.parseInt(Streams.unboxChars(s.chars()
                .map(c -> switch (c) {
                    case 'F', 'L' -> '0';
                    case 'B', 'R' -> '1';
                    default -> c;
                })), 2)).collect(Collectors.toSet());
        IntSummaryStatistics stats = Streams.unboxInts(seats).summaryStatistics();

        int min = stats.getMin();
        int max = stats.getMax();

        int fakeSum = Math.multiplyExact(max - min + 1, (max + min) / 2);
        long realSum = stats.getSum();

        return new Result(max, fakeSum - realSum);
    }
}
