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

        return new Result(validCount, positionCount);
    }
}
