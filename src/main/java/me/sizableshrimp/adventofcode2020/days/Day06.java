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

import me.sizableshrimp.adventofcode2020.helper.Processor;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day06 extends SeparatedDay {
    private List<String[]> groups;

    @Override
    protected Object part1() {
        return sum(Processor::unionArray);
    }

    @Override
    protected Object part2() {
        return sum(Processor::intersectionArray);
    }

    private int sum(BiFunction<Set<Character>, char[], Set<Character>> func) {
        int sum = 0;
        for (String[] group : groups) {
            Set<Character> result = null;
            for (String person : group) {
                result = func.apply(result, person.toCharArray());
            }
            sum += result == null ? 0 : result.size();
        }
        return sum;
    }

    @Override
    protected void parse() {
        groups = Processor.splitStream(lines, String::isBlank)
                .map(s -> s.toArray(String[]::new))
                .collect(Collectors.toList());
    }
}
