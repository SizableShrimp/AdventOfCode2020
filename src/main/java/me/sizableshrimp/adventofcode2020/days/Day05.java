package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.templates.Day;
import one.util.streamex.IntStreamEx;

import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Set;

public class Day05 extends Day {
    @Override
    protected Result evaluate() {
        Set<Integer> seats = new HashSet<>();

        for (String seat : lines) {
            Integer binSeat = Integer.parseInt(IntStreamEx.ofChars(seat)
                    .map(c -> switch (c) {
                        case 'F', 'L' -> '0';
                        case 'B', 'R' -> '1';
                        default -> throw new IllegalStateException("Unexpected value: " + c);
                    }).charsToString(), 2);
            seats.add(binSeat);
        }

        IntSummaryStatistics stats = IntStreamEx.of(seats).summaryStatistics();
        int min = stats.getMin();
        int max = stats.getMax();

        int fakeSum = Math.multiplyExact(max - min + 1, (max + min) / 2);
        long realSum = stats.getSum();

        return Result.of(max, fakeSum - realSum);
    }
}
