/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.ListConvert;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day01 extends SeparatedDay {
    private List<Integer> expenseReport;

    @Override
    protected Object part1() {
        return traverse(true);
    }

    @Override
    protected Object part2() {
        return traverse(false);
    }

    private Object traverse(boolean returnEarly) {
        return traverse(returnEarly, false, 2020);
    }

    private int traverse(boolean returnEarly, boolean inner, int target) {
        Set<Integer> seen = new HashSet<>();
        for (int current : expenseReport) {
            int diff = target - current;
            if (diff <= 0)
                continue;
            if (returnEarly) {
                if (seen.contains(diff))
                    return inner ? diff : current * diff;
            } else {
                int third = traverse(true, true, diff);
                if (third != -1)
                    return current * (diff - third) * third;
            }
            seen.add(current);
        }
        return -1;
    }

    // private int getProductOfNSum(int n) {
    //     return Itertools.combinations(expenseReport, n).stream()
    //             .filter(l -> Streams.unboxInts(l).sum() == 2020)
    //             .findFirst().get()
    //             .stream()
    //             .reduce((a, b) -> a * b).get();
    // }

    @Override
    protected void parse() {
        expenseReport = ListConvert.ints(lines);
    }
}
