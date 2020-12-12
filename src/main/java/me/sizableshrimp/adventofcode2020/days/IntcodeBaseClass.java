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

import me.sizableshrimp.adventofcode2020.intcode.Instruction;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.List;

public class IntcodeBaseClass extends Day {
    private List<Instruction> baseInstructions;

    @Override
    protected Result evaluate() {
        Result result = new Result();

        return result;
    }

    @Override
    protected void parse() {
        baseInstructions = Instruction.parseLines(lines);
    }
}
