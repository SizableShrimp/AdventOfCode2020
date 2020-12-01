/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.templates;

import lombok.Getter;
import me.sizableshrimp.adventofcode2020.helper.DataManager;

import java.util.List;
import java.util.Objects;

/**
 * A single day which has two challenges to solve.
 * There are 25 days in <a href="http://adventofcode.com">Advent Of Code</a>.
 * Each day has two parts to it to solve the entire day.
 */
public abstract class Day {
    @Getter
    private String part1;
    @Getter
    private String part2;

    /**
     * The lines parsed from the input file for the challenge. For example, an input file with the data:
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
     * Execute a given day; outputting part 1, part 2, and the time taken.
     */
    public void run() {
        long before = System.nanoTime();
        parse();
        evaluate();
        long after = System.nanoTime();
        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);
        System.out.printf("Completed in %.3fs%n%n", (after - before) / 1_000_000_000f);
    }

    /**
     * This internal method is what actually evaluates the result of part 1 and part 2.
     */
    protected abstract void evaluate();

    /**
     * This internal method can be overridden to parse the {@link #lines} of the day into something more useful for
     * the challenge.
     * <p>
     * This method will automatically be run before {@link #evaluate()}.
     */
    protected void parse() {}

    protected void setPart1(Object obj) {
        part1 = Objects.toString(obj);
    }

    protected void setPart2(Object obj) {
        part2 = Objects.toString(obj);
    }
}
