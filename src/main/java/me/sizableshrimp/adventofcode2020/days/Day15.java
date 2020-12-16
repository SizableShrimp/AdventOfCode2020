package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.templates.Day;

public class Day15 extends Day {
    private int[] startNums;

    @Override
    protected Result evaluate() {
        Result result = new Result();
        int inputSize = startNums.length;
        int[] data = new int[30_000_000];

        // Fill the starting data
        int last = -1;
        for (int turn = 1; turn <= inputSize; turn++) {
            last = startNums[turn - 1];
            if (turn != inputSize)
                data[last] = turn;
        }

        // Calculate
        for (int turn = inputSize + 1; ; turn++) {
            if (turn == 2021) {
                result.setPart1(last);
            } else if (turn == 30_000_001) {
                result.setPart2(last);
                break;
            }
            int index = data[last];
            data[last] = turn - 1;
            last = index == 0 ? 0 : (turn - 1) - index;
        }

        return result;
    }

    @Override
    protected void parse() {
        String[] split = lines.get(0).split(",");
        startNums = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            startNums[i] = Integer.parseInt(split[i]);
        }
    }
}
