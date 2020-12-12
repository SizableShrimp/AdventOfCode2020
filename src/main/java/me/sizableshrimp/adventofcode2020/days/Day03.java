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

import me.sizableshrimp.adventofcode2020.templates.Coordinate;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.Arrays;

public class Day03 extends Day {
    @Override
    protected Result evaluate() {
        Coordinate[] slopes = {Coordinate.of(1, 1), Coordinate.of(3, 1), Coordinate.of(5, 1),
                Coordinate.of(7, 1), Coordinate.of(1, 2)};
        long[] results = new long[slopes.length];

        // Slower
        // boolean[][] grid = GridHelper.convertBool((y, x) -> new boolean[y][x], lines, c -> c == '#');
        int length = lines.get(0).length();

        for (int i = 0; i < slopes.length; i++) {
            Coordinate next = slopes[i];
            Coordinate add = next;
            long count = 0;
            while (next.y < lines.size()) {
                boolean hasTree = lines.get(next.y).charAt(next.x % length) == '#';
                if (hasTree)
                    count++;
                next = next.resolve(add);
            }
            results[i] = count;
        }

        return new Result(results[1], Arrays.stream(results).reduce((a, b) -> a * b).getAsLong());
    }
}
