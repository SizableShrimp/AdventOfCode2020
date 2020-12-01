/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.templates;

/**
 * A {@link Day} which has both parts of the challenge separated into different internal methods.
 * This might be applied to a completed day after I have solved it and believe it would promote readability to
 * separate the parts.
 */
public abstract class SeparatedDay extends Day {
    /**
     * @return The result of part 1
     */
    protected abstract Object part1();

    /**
     * @return The result of part 2
     */
    protected abstract Object part2();

    @Override
    protected final void evaluate() {
        setPart1(part1());
        setPart2(part2());
    }
}
