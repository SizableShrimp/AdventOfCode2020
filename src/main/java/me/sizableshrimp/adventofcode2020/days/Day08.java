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
import me.sizableshrimp.adventofcode2020.intcode.Intcode;
import me.sizableshrimp.adventofcode2020.intcode.OpCode;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08 extends Day {
    private List<Instruction> baseInstructions;

    @Override
    protected Result evaluate() {
        Intcode.ExitLoop exitLoop = new Intcode(baseInstructions).runUntilRepeat();
        Result result = new Result(exitLoop.getAccumulator(), null);

        Set<Instruction> possible = exitLoop.getSeen().stream()
                .filter(inst -> inst.getCode() == OpCode.JUMP || inst.getCode() == OpCode.NOP)
                .collect(Collectors.toSet());

        for (Instruction inst : possible) {
            Instruction opposite = switch (inst.getCode()) {
                case JUMP -> inst.changeCode(OpCode.NOP);
                case NOP -> inst.changeCode(OpCode.JUMP);
                default -> throw new IllegalStateException("wrong");
            };
            List<Instruction> copy = new ArrayList<>(baseInstructions);
            copy.set(opposite.getIndex(), opposite);
            Intcode.ExitState exitState = new Intcode(copy).runUntilExitOrRepeat();
            if (exitState.isExited()) {
                result.setPart2(exitState.getAccumulator());
                break;
            }
        }

        return result;
    }

    @Override
    protected void parse() {
        baseInstructions = Instruction.parseLines(lines);
    }
}
