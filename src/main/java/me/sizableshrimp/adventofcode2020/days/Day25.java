package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.ListConvert;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.List;

public class Day25 extends SeparatedDay {
    private int firstPubKey;
    private int secondPubKey;

    @Override
    protected Object part1() {
        long value = 1;
        for (int loopSize = 1; ; loopSize++) {
            value *= 7;
            value %= 20201227;
            if (value == firstPubKey) {
                return transformSubject(loopSize, secondPubKey);
            } else if (value == secondPubKey) {
                return transformSubject(loopSize, firstPubKey);
            }
        }
    }

    @Override
    protected Object part2() {
        // There is no part 2 :)
        return null;
    }

    private long transformSubject(int loopSize, int subject) {
        long value = 1;

        for (int i = 0; i < loopSize; i++) {
            value *= subject;
            value %= 20201227;
        }

        return value;
    }

    @Override
    protected void parse() {
        List<Integer> ints = ListConvert.ints(lines);
        firstPubKey = ints.get(0);
        secondPubKey = ints.get(1);
    }
}
