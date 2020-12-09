/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.intcode.Instruction;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.List;

public class IntcodeBaseClass extends Day {
    private List<Instruction> baseInstructions;

    @Override
    protected Result evaluate() {
        return new Result();
    }

    @Override
    protected void parse() {
        baseInstructions = Instruction.parseLines(lines);
    }
}
