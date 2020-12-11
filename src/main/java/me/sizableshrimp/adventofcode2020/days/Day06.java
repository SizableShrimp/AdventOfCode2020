/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
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
