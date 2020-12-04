/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.templates.Coordinate;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends Day {
    @Override
    protected Result evaluate() {
        List<Coordinate> slopes = List.of(new Coordinate(1, 1), new Coordinate(3, 1), new Coordinate(5, 1),
                new Coordinate(7, 1), new Coordinate(1, 2));
        List<Long> results = new ArrayList<>(slopes.size());

        // Slower
        // boolean[][] grid = GridHelper.convertBool((y, x) -> new boolean[y][x], lines, c -> c == '#');
        int length = lines.get(0).length();

        for (Coordinate next : slopes) {
            Coordinate add = next;
            long count = 0;
            while (next.y < lines.size()) {
                boolean hasTree = lines.get(next.y).charAt(next.x % length) == '#';
                if (hasTree)
                    count++;
                next = next.resolve(add);
            }
            results.add(count);
        }

        return new Result(results.get(1), results.stream().reduce((a, b) -> a * b).get());
    }
}
