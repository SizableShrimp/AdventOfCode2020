package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.MatchWrapper;
import me.sizableshrimp.adventofcode2020.helper.Parser;
import me.sizableshrimp.adventofcode2020.templates.Day;

public class Day02 extends Day {
    @Override
    protected Result evaluate() {
        int validCount = 0;
        int positionCount = 0;

        for (String line : lines) {
            MatchWrapper match = Parser.parseMatch("(\\d+)-(\\d+) (\\w): (\\w+)", line);

            int least = match.groupInt(1);
            int most = match.groupInt(2);
            char policy = match.groupChar(3);
            String password = match.group(4);

            long num = password.chars()
                    .filter(c -> c == policy)
                    .count();

            if (num >= least && num <= most)
                validCount++;
            // XOR
            if ((password.charAt(least - 1) == policy) != (password.charAt(most - 1) == policy))
                positionCount++;
        }

        return Result.of(validCount, positionCount);
    }
}
