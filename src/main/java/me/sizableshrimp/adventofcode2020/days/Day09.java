/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

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
        for (int i = 25; i < all.size(); i++) {
            long input = all.get(i);
            List<Long> window = all.subList(i - 25, i);

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

        for (int j = 2; j < all.size(); j++) {
            for (int i = part1 - j; i >= 0; i--) {
                List<Long> window = all.subList(i, i + j);
                LongSummaryStatistics stats = Streams.unboxLongs(window).summaryStatistics();
                if (stats.getSum() < target) {
                    // If the current sum is less than the target, we have gone past the
                    // possible answer which means this window size is not correct.
                    break;
                } else if (stats.getSum() == target) {
                    return stats.getMin() + stats.getMax();
                }
            }
        }

        return null;
    }

    @Override
    protected void parse() {
        all = ListConvert.longs(lines);
    }
}
