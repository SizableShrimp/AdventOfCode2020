package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.ArrayConvert;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.HashSet;
import java.util.Set;

public class Day01 extends SeparatedDay {
    private int[] expenseReport;

    @Override
    protected Object part1() {
        return traverse(true);
    }

    @Override
    protected Object part2() {
        return traverse(false);
    }

    private Object traverse(boolean returnEarly) {
        return traverse(returnEarly, new HashSet<>(), false, 2020);
    }

    private int traverse(boolean returnEarly, Set<Integer> seen, boolean inner, int target) {
        for (int current : expenseReport) {
            int diff = target - current;
            if (diff <= 0)
                continue;
            if (returnEarly) {
                if (seen.contains(diff))
                    return inner ? diff : current * diff;
            } else {
                int third = traverse(true, seen, true, diff);
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
        expenseReport = ArrayConvert.ints(lines);
    }
}
