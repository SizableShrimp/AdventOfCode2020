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

package me.sizableshrimp.adventofcode2020.intcode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter(AccessLevel.PACKAGE)
public class Intcode {
    private final List<Instruction> instructions;
    @Setter(AccessLevel.PACKAGE)
    private long accumulator;
    private boolean exit;
    private int pointer; // Current index

    public Intcode(List<Instruction> instructions) {
        this.accumulator = 0;
        this.instructions = instructions;
        this.pointer = 0;
    }

    public ExitState runUntilExit() {
        while (!exit) {
            if (pointer < 0 || pointer >= instructions.size()) {
                return new ExitState(true, accumulator);
            }
            Instruction op = instructions.get(pointer);
            pointer = calculate(op);
        }
        return new ExitState(true, accumulator);
    }

    public int calculate(Instruction inst) {
        Integer next = inst.getCode().apply(this, inst);
        // Currently only supports RELATIVE mode
        return pointer + Objects.requireNonNullElse(next, 1);
    }

    public ExitLoop runUntilRepeat() {
        Set<Instruction> seen = new HashSet<>();
        while (true) {
            Instruction op = instructions.get(pointer);
            if (!seen.add(op))
                break;
            pointer = calculate(op);
        }
        return new ExitLoop(accumulator, pointer, seen);
    }

    public ExitState runUntilExitOrRepeat() {
        Set<Instruction> seen = new HashSet<>();
        while (!exit) {
            if (pointer < 0 || pointer >= instructions.size()) {
                return new ExitState(true, accumulator);
            }
            Instruction op = instructions.get(pointer);
            if (!seen.add(op))
                break;
            pointer = calculate(op);
        }
        return new ExitLoop(accumulator, pointer, seen);
    }

    long adjustAccumulator(long amount) {
        accumulator += amount;
        return accumulator;
    }

    void exit() {
        exit = true;
    }

    @Getter
    public static class ExitLoop extends ExitState {
        final int loopIndex;
        final Set<Instruction> seen;

        ExitLoop(long accumulator, int loopIndex, Set<Instruction> seen) {
            super(false, accumulator);
            this.loopIndex = loopIndex;
            this.seen = seen;
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    public static class ExitState {
        protected boolean exited;
        protected long accumulator;
    }

    // @AllArgsConstructor
    // private enum ParameterMode {
    //     POSITION_MODE(0), IMMEDIATE_MODE(1), RELATIVE_MODE(2);
    //
    //     int id;
    //
    //     public static ParameterMode getMode(int id) {
    //         for (ParameterMode value : values()) {
    //             if (value.id == id)
    //                 return value;
    //         }
    //         return POSITION_MODE;
    //     }
    //
    //     public long getLongFromMode(long input, Intcode ic, boolean set) {
    //         switch (this) {
    //             case POSITION_MODE:
    //                 return set ? input : ic.getMemory(input);
    //             case IMMEDIATE_MODE:
    //                 return input;
    //             case RELATIVE_MODE:
    //                 return set ? input + ic.getRelativeBase() : ic.getMemory(ic.getRelativeBase() + input);
    //             default:
    //                 throw new IllegalArgumentException();
    //         }
    //     }
    // }
}
