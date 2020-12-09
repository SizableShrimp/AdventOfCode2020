/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
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
