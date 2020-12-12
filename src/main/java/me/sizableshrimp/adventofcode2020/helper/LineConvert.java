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

package me.sizableshrimp.adventofcode2020.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LineConvert {
    /**
     * Find all integers in a line.
     *
     * @param line The string line.
     * @return The integers found in the line.
     */
    public static List<Integer> ints(String line) {
        Matcher m = Pattern.compile("-?\\d+").matcher(line);
        List<Integer> result = new ArrayList<>();
        while (m.find()) {
            result.add(Integer.parseInt(m.group(0)));
        }
        return result;
    }

    public static List<Long> longs(String line) {
        Matcher m = Pattern.compile("-?\\d+").matcher(line);
        List<Long> result = new ArrayList<>();
        while (m.find()) {
            result.add(Long.parseLong(m.group(0)));
        }
        return result;
    }

    public static List<Character> chars(String s) {
        return s.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toList());
    }
}
