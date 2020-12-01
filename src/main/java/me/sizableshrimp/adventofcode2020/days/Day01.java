/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.Itertools;
import me.sizableshrimp.adventofcode2020.helper.ListConvert;
import me.sizableshrimp.adventofcode2020.helper.Streams;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.List;

public class Day01 extends SeparatedDay {
    private List<Integer> expenseReport;

    @Override
    protected Object part1() {
        return getProductOfNSum(2);
    }

    @Override
    protected Object part2() {
        return getProductOfNSum(3);
    }

    private int getProductOfNSum(int n) {
        return Itertools.combinations(expenseReport, n).stream()
                .filter(l -> Streams.unboxIntList(l).sum() == 2020)
                .findFirst().get()
                .stream()
                .reduce((a, b) -> a * b).get();
    }

    @Override
    protected void parse() {
        expenseReport = ListConvert.ints(lines);
    }
}
