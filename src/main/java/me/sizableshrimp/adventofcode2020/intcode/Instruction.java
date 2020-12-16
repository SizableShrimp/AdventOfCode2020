package me.sizableshrimp.adventofcode2020.intcode;

import lombok.Value;
import me.sizableshrimp.adventofcode2020.helper.MatchWrapper;
import me.sizableshrimp.adventofcode2020.helper.Parser;

import java.util.ArrayList;
import java.util.List;

@Value
public class Instruction {
    int index;
    OpCode code;
    int[] args;

    public static List<Instruction> parseLines(List<String> lines) {
        List<Instruction> instructions = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            instructions.add(Instruction.parse(i, line));
        }

        return instructions;
    }

    public static Instruction parse(int index, String line) {
        MatchWrapper match = Parser.parseMatch("(\\w+?) (.+?)", line);

        OpCode code = OpCode.getOpCode(match.group(1));

        String[] split = match.group(2).split(" ");
        int[] args = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            String arg = split[i];
            args[i] = Integer.parseInt(arg);
        }

        return new Instruction(index, code, args);
    }

    /**
     * Returns a copy of the current {@link Instruction} with a modified {@link OpCode}.
     *
     * @param newCode The new {@link OpCode} to use.
     * @return A copy of the current instance with a modified {@link OpCode}.
     */
    public Instruction changeCode(OpCode newCode) {
        return new Instruction(index, newCode, args);
    }
}
