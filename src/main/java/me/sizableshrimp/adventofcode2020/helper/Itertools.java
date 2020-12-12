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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//see Python Itertools
public class Itertools {
    @SafeVarargs
    public static <T> List<List<T>> product(List<T>... lists) {
        return product(1, lists);
    }

    @SafeVarargs
    public static <T> List<List<T>> product(int repeat, List<T>... lists) {
        List<List<T>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        List<List<T>> pools = new ArrayList<>(Arrays.asList(lists));
        pools = repeat(pools, repeat);
        for (List<T> pool : pools) {
            List<List<T>> newResult = new ArrayList<>();
            for (List<T> x : result) {
                for (T y : pool) {
                    List<T> list = new ArrayList<>(x);
                    list.add(y);
                    newResult.add(list);
                }
            }
            result = newResult;
        }

        return result;
    }

    private static <T> List<List<T>> repeat(List<List<T>> pools, int repeat) {
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < repeat; i++) {
            for (List<T> pool : pools) {
                result.add(new ArrayList<>(pool));
            }
        }
        return result;
    }

    /**
     * Returns a List of combinations of the input collection, where each combination is of length r and sorted by
     * lexicographic order.
     *
     * @param collection The collection of elements to create combinations of.
     * @param r The size of each combination.
     * @param <T> The type of element in the input collection.
     * @return A List of combinations of the input collection, where each combination is of length r and sorted by
     * lexicographic order.
     * @see
     * <a href=https://docs.python.org/2/library/itertools.html#itertools.combinations>Python itertools.combinations</a>
     */
    public static <T> List<List<T>> combinations(Collection<T> collection, int r) {
        List<List<T>> result = new ArrayList<>();
        List<T> pool = new ArrayList<>(collection);
        int n = pool.size();
        int[] indices = IntStream.range(0, r).toArray();
        result.add(yieldResult(pool, indices));
        while (true) {
            boolean broke = false;
            int last = 0;
            for (int i : IntStream.range(0, r).mapToObj(i -> r - i - 1).collect(Collectors.toList())) {
                last = i;
                if (indices[i] != i + n - r) {
                    broke = true;
                    break;
                }
            }
            if (!broke)
                return result;

            indices[last]++;
            IntStream.range(last + 1, r).forEach(j -> indices[j] = indices[j - 1] + 1);
            result.add(yieldResult(pool, indices));
        }
    }

    private static <T> List<T> yieldResult(List<T> pool, int[] indices) {
        List<T> result = new ArrayList<>();
        for (int index : indices) {
            result.add(pool.get(index));
        }

        return result;
    }
}
