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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutator {
    /**
     * Permutes the input list into a set of all possible permutations.
     *
     * @param input The input list.
     * @param <T> The type of the list used when permuting.
     * @return A set of all possible permutations of the input list.
     */
    public static <T> Set<List<T>> permute(List<T> input) {
        return internalPermute(new ArrayList<>(input));
    }

    private static <T> Set<List<T>> internalPermute(List<T> base) {
        if (base.isEmpty()) {
            Set<List<T>> perms = new HashSet<>();
            perms.add(new ArrayList<>());
            return perms;
        }

        T first = base.remove(0);
        Set<List<T>> result = new HashSet<>();
        Set<List<T>> permutations = internalPermute(base);
        for (var list : permutations) {
            for (int i = 0; i <= list.size(); i++) {
                List<T> temp = new ArrayList<>(list);
                temp.add(i, first);
                result.add(temp);
            }
        }

        return result;
    }
}
