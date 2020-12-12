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
