package me.sizableshrimp.adventofcode2020.days;

import lombok.Value;
import me.sizableshrimp.adventofcode2020.helper.Parser;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;
import one.util.streamex.LongStreamEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongConsumer;

public class Day14 extends SeparatedDay {
    private List<BitOp> ops;

    @Override
    protected Long part1() {
        return runDecoder(false);
    }

    @Override
    protected Long part2() {
        return runDecoder(true);
    }

    private long runDecoder(boolean versionTwo) {
        Map<Long, Long> memory = new HashMap<>();
        String mask = "";

        for (BitOp op : ops) {
            if (op.isMask) {
                mask = op.value;
                continue;
            }

            long toPass = versionTwo ? op.address : op.longValue;
            LongConsumer consumer = versionTwo
                    ? address -> memory.put(address, op.longValue)
                    : value -> memory.put(op.address, value);
            calcMask(mask, toPass, versionTwo, consumer);
        }

        return LongStreamEx.of(memory.values()).sum();
    }

    private void calcMask(String mask, long l, boolean floating, LongConsumer consumer) {
        List<Integer> xs = new ArrayList<>();

        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c == '1') {
                l |= (1L << (mask.length() - i - 1));
            } else if (c == 'X' && floating) {
                xs.add(mask.length() - i - 1);
            } else if (c == '0' && !floating) {
                l &= ~(1L << (mask.length() - i - 1));
            }
        }
        if (!floating) {
            consumer.accept(l);
            return;
        }

        int amount = (int) (Math.pow(2, xs.size()));
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < xs.size(); j++) {
                long bit = (i & (1L << j)) >> j;
                if (bit == 1) {
                    l |= (1L << xs.get(j));
                } else {
                    l &= ~(1L << xs.get(j));
                }
            }
            consumer.accept(l);
        }
    }

    @Override
    protected void parse() {
        ops = new ArrayList<>(lines.size());

        for (String line : lines) {
            String[] split = line.split(" = ");
            boolean isMask = line.startsWith("mask");
            long address = -1;
            String value = split[1];
            long longValue = -1;
            if (!isMask) {
                address = Parser.parseMatch("mem\\[(\\d+)]", split[0]).groupLong(1);
                longValue = Long.parseLong(value);
                value = null;
            }

            ops.add(new BitOp(isMask, address, value, longValue));
        }
    }

    @Value
    private static class BitOp {
        boolean isMask;
        long address;
        String value;
        long longValue;
    }
}
