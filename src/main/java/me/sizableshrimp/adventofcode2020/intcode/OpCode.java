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

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum OpCode {
    ACCUMULATE("acc", (ic, op) -> {
        ic.adjustAccumulator(op.getArgs()[0]);
        return null;
    }),
    JUMP("jmp", (ic, op) -> op.getArgs()[0]),
    NOP("nop", (ic, op) -> null);

    final String code;
    @Delegate
    BiFunction<Intcode, Instruction, Integer> operation;

    private static final Map<String, OpCode> codes = Arrays.stream(values())
            .collect(Collectors.toMap(oc -> oc.code, Function.identity()));

    public static OpCode getOpCode(String code) {
        OpCode opcode = codes.get(code);
        if (opcode != null)
            return opcode;
        throw new IllegalArgumentException();
    }
}
