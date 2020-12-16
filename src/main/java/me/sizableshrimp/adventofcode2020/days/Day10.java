package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.ListConvert;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.HashSet;
import java.util.Set;

public class Day10 extends SeparatedDay {
    private static final int[] EXPANSION = {1, 1, 2, 4, 7};
    private int[] adapters;
    private int[] diffs;

    @Override
    protected Object part1() {
        // Slower one-liner (StreamEx is awesome!!!!)
        // return IntStreamEx.of(IntStreamEx.of(adapters).boxed()
        //         .pairMap((a, b) -> b - a)
        //         .groupingBy(Function.identity(), MoreCollectors.countingInt())
        //         .values())
        //         .reduce((a, b) -> a * b).getAsInt();

        diffs = new int[adapters.length - 1];
        for (int i = 0; i < diffs.length; i++) {
            diffs[i] = adapters[i + 1] - adapters[i];
        }

        int one = 0;
        int three = 0;

        for (int i = 0; i < adapters.length - 1; i++) {
            int adapter = adapters[i];
            int next = adapters[i + 1];
            if (next - adapter == 1) {
                one++;
            } else {
                three++;
            }
        }

        return one * three;
    }

    @Override
    protected Object part2() {
        // Slower one-liner (StreamEx is awesome!!!!)
        // return IntStreamEx.of(adapters).boxed()
        //         .collapse((a, b) -> b - a == 1, Collectors.counting())
        //         .mapToLong(l -> EXPANSION[l.intValue() - 1])
        //         .reduce((a, b) -> a * b).getAsLong();

        long val = 1;
        int i = 0;

        while (i < adapters.length - 1) {
            if (diffs[i] != 1) {
                i++;
                continue;
            }

            int counter = 0;
            do {
                counter++;
                i++;
            } while (diffs[i] == 1);

            val *= EXPANSION[counter];
        }

        return val;
    }

    @Override
    protected void parse() {
        Set<Integer> set = new HashSet<>(ListConvert.ints(lines));
        set.add(0);

        adapters = new int[set.size() + 1];

        // Sets are ordered based on their hash, and a hash of an int is the int itself...
        int i = 0;
        for (int adapter : set) {
            adapters[i++] = adapter;
        }

        adapters[adapters.length - 1] = adapters[adapters.length - 2] + 3;
    }
}
