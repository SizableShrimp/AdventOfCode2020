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

/**
 * A {@link Day} which has both parts of the challenge separated into different internal methods.
 * This might be applied to a completed day after I have solved it and believe it would promote readability to
 * separate the parts.
 */
public abstract class SeparatedDay extends Day {
    @Override
    protected final Result evaluate() {
        return new Result(part1(), part2());
    }

    /**
     * @return The result of part 1
     */
    protected abstract Object part1();

    /**
     * @return The result of part 2
     */
    protected abstract Object part2();
}
