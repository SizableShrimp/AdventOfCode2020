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

package me.sizableshrimp.adventofcode2020.templates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.sizableshrimp.adventofcode2020.helper.DataManager;

import java.util.List;
import java.util.Objects;

/**
 * A single day which has two challenges to solve.
 * There are 25 days in <a href="http://adventofcode.com">Advent Of Code</a>.
 * Each day has two parts to it to solve the entire day.
 */
public abstract class Day {
    /**
     * An <b>unmodifiable</b>> list of the lines parsed from the input file for the challenge.
     * For example, an input file with the data:
     * <pre>{@code 1
     * 2
     * 3
     * 4
     * 5
     * }</pre>
     * would be parsed as {"1", "2", "3", "4", "5"}.
     * <br />
     * <b>NOTE:</b> This variable is assigned using {@link DataManager#read}, which means it has the possibility to hit
     * the Advent Of Code servers to request the input data. See {@link DataManager#read} for more details.
     */
    protected List<String> lines = DataManager.read(Integer.parseInt(getClass().getSimpleName().substring(3)));
    /**
     * The raw file input, denoting lines by <b>Unix-style endings</b> or <code>\n</code>.
     */
    protected String raw = String.join("\n", lines);

    /**
     * Execute a given day; outputting part 1, part 2, and the time taken.
     * Time taken is using {@link System#nanoTime()} and is not a real benchmark.
     *
     * @return A {@link Result} holding data of the first and second part.
     */
    public final Result run() {
        long before = System.nanoTime();
        Result result = parseAndEvaluate();
        long after = System.nanoTime();
        float time = (after - before) / 1_000_000f;
        System.out.println("Part 1: " + result.part1);
        System.out.println("Part 2: " + result.part2);
        System.out.printf("Completed in %.3fms%n%n", time);
        return result;
    }

    /**
     * Parse and then evaluate a day's code.
     * This should be guaranteed to be repeatable without constructing a new instance of the class.
     *
     * @return A {@link Result} holding data of the first and second part.
     */
    public final Result parseAndEvaluate() {
        parse();
        return evaluate();
    }

    /**
     * This internal method is what actually evaluates the result of part 1 and part 2.
     */
    protected abstract Result evaluate();

    /**
     * @deprecated This should only be using for benchmarking purposes. Other uses are not supported.
     */
    @Deprecated
    public final void parseTesting() {
        parse();
    }

    /**
     * This internal method can be overridden to parse the {@link #lines} of the day into something more useful for
     * the challenge.
     * <p>
     * This method will automatically be run before {@link #evaluate()}.
     */
    protected void parse() {}

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        Object part1;
        Object part2;

        public String getPart1() {
            return Objects.toString(part1);
        }

        public String getPart2() {
            return Objects.toString(part2);
        }

        public Object getPart1Obj() {
            return part1;
        }

        public Object getPart2Obj() {
            return part2;
        }
    }
}
