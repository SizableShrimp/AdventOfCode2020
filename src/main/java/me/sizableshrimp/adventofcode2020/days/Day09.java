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

import me.sizableshrimp.adventofcode2020.helper.ArrayConvert;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.Arrays;
import java.util.LongSummaryStatistics;
import java.util.stream.LongStream;

public class Day09 extends SeparatedDay {
    private long[] data;
    private int part1;

    @Override
    protected Object part1() {
        int windowSize = 25;
        for (int i = windowSize; i < data.length; i++) {
            long input = data[i];
            int start = i - windowSize;

            boolean found = false;
            for (int j = start; j < i; j++) {
                long num = data[j];
                found = contains(data, start, i, input - num);
                if (found)
                    break;
            }

            if (!found) {
                part1 = i;
                return input;
            }
        }

        return null;
    }

    @Override
    protected Object part2() {
        if (part1 == 0)
            return null;
        long target = data[part1];

        int left = part1 - 1;
        int right = part1;
        long sum = data[left] + data[right];

        while (true) {
            if (sum < target) {
                // If the current sum is less than the target, we don't have enough numbers
                // and need to slide our left bound to the left to get a higher sum.

                // System.out.println("too low: " + left + " - " + right);
                left--;
                sum += data[left];
            } else if (sum > target) {
                // If the current sum is more than the target, we have too many numbers
                // and need to slide our right bound to the left to get a lower sum.

                // System.out.println("too high: " + left + " - " + right);
                right--;
                sum -= data[right + 1];
            } else {
                long[] window = Arrays.copyOfRange(data, left, right);
                LongSummaryStatistics stats = LongStream.of(window).summaryStatistics();
                return stats.getMin() + stats.getMax();
            }
        }
    }

    private boolean contains(long[] data, int start, int end, long find) {
        for (int i = start; i < end; i++) {
            if (data[i] == find)
                return true;
        }

        return false;
    }

    @Override
    protected void parse() {
        data = ArrayConvert.longs(lines);
    }
}
