/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.ArrayConvert;
import me.sizableshrimp.adventofcode2020.helper.ListConvert;
import me.sizableshrimp.adventofcode2020.helper.Streams;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.List;
import java.util.LongSummaryStatistics;

public class Day09 extends SeparatedDay {
    private List<Long> all;
    private int part1;

    @Override
    protected Object part1() {
        int windowSize = 25;
        for (int i = windowSize; i < all.size(); i++) {
            long input = all.get(i);
            List<Long> window = all.subList(i - windowSize, i);

            boolean found = false;
            for (long l : window) {
                if (window.contains(input - l)) {
                    found = true;
                    break;
                }
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
        long target = all.get(part1);
        long[] prefixSums = ArrayConvert.prefixSumsLong(all);

        // System.out.println(part1);
        int left = part1 - 1;
        int right = part1;

        while (true) {
            long sum = prefixSums[right] - prefixSums[left - 1];

            if (sum < target) {
                // If the current sum is less than the target, we don't have enough numbers
                // and need to slide our left bound to the left to get a higher sum.

                // System.out.println("too low: " + left + " - " + right);
                left--;
            }  else if (sum > target) {
                // If the current sum is more than the target, we have too many numbers
                // and need to slide our right bound to the left to get a lower sum.

                // System.out.println("too high: " + left + " - " + right);
                right--;
            } else {
                List<Long> window = all.subList(left, right);
                LongSummaryStatistics stats = Streams.unboxLongs(window).summaryStatistics();
                return stats.getMin() + stats.getMax();
            }
        }
    }

    @Override
    protected void parse() {
        all = ListConvert.longs(lines);
    }
}
