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
import me.sizableshrimp.adventofcode2020.templates.Direction;

public class Day12 extends Day {
    @Override
    protected Result evaluate() {
        Coordinate part1 = Coordinate.ZERO;
        Coordinate part2 = Coordinate.ZERO;
        Coordinate waypoint = Coordinate.of(10, -1);
        Direction dir = Direction.EAST;

        for (String line : lines) {
            char c = line.charAt(0);
            int num = Integer.parseInt(line.substring(1));
            switch (c) {
                case 'N', 'E', 'S', 'W' -> {
                    Direction temp = Direction.getCardinalDirection(c);
                    Coordinate mult = Coordinate.of(temp.x * num, temp.y * num);
                    // Part 1
                    part1 = part1.resolve(mult);
                    // Part 2
                    waypoint = waypoint.resolve(mult);
                }
                case 'L', 'R' -> {
                    int degrees = c == 'L' ? -num : num;
                    // Part 1
                    dir = dir.relativeDegrees(degrees);
                    // Part 2
                    waypoint = waypoint.rotate90(degrees);
                }
                case 'F' -> {
                    for (int i = 0; i < num; i++) {
                        // Part 1
                        part1 = part1.resolve(dir);
                        // Part 2
                        part2 = part2.resolve(waypoint);
                    }
                }
            }
        }

        return new Result(part1.distanceZero(), part2.distanceZero());
    }
}
