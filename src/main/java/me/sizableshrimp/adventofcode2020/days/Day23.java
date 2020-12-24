package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

public class Day23 extends SeparatedDay {
    private int start;
    private int[] part1;
    private int[] part2;

    @Override
    protected Object part1() {
        // Maps current cup value -> next cup value
        int[] cups = playCupsGame(part1, 100);

        StringBuilder builder = new StringBuilder();
        int current = cups[1];
        do {
            builder.append(current);
            current = cups[current];
        } while (current != 1);

        return builder.toString();
    }

    @Override
    protected Object part2() {
        int[] cups = playCupsGame(part2, 10_000_000);
        return (long) cups[1] * cups[cups[1]];
    }

    private int[] playCupsGame(int[] cups, int moves) {
        int current = start;

        for (int move = 0; move < moves; move++) {
            current = doMove(cups, current);
        }

        return cups;
    }

    private int doMove(int[] cups, int current) {
        int startSub = cups[current];
        int middleSub = cups[startSub];
        int endSub = cups[middleSub];
        cups[current] = cups[endSub];

        int destination = current;
        do {
            destination--;
            if (destination <= 0) {
                destination = cups.length - 1;
            }
        } while (destination == startSub || destination == middleSub || destination == endSub);

        int after = cups[destination];
        cups[destination] = startSub;
        cups[endSub] = after;

        return cups[current];
    }

    @Override
    protected void parse() {
        String line = lines.get(0);
        part1 = new int[line.length() + 1];
        part2 = new int[1_000_001];

        int max = -1;
        int[] raw = new int[1_000_000];
        String[] split = line.split("");

        for (int i = 0; i < split.length; i++) {
            int digit = Integer.parseInt(split[i]);
            raw[i] = digit;
            if (digit > max)
                max = digit;
        }
        for (int i = max; i < 1_000_000; i++) {
            raw[i] = i + 1;
        }

        for (int i = 0; i < raw.length; i++) {
            int digit = raw[i];
            if (i == 0)
                start = digit;
            if (i < split.length)
                part1[digit] = raw[(i + 1) % split.length];
            part2[digit] = raw[(i + 1) % raw.length];
        }
    }
}
