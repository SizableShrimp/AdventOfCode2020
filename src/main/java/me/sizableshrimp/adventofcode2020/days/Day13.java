package me.sizableshrimp.adventofcode2020.days;

import lombok.Value;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends SeparatedDay {
    private final int earliest = Integer.parseInt(lines.get(0));
    private List<Bus> buses;

    @Override
    protected Object part1() {
        int min = Integer.MAX_VALUE;
        int minId = -1;

        for (Bus bus : buses) {
            int nextAvailable = Math.floorMod(-earliest, bus.id);
            if (nextAvailable < min) {
                min = nextAvailable;
                minId = bus.id;
            }
        }

        return minId * min;
    }

    @Override
    protected Object part2() {
        // There are probably better ways to do this, but I barely get the chinese remainder theorem
        long bigN = 1;
        for (Bus bus : buses) {
            bigN *= bus.id;
        }
        long result = 0;
        for (Bus bus : buses) {
            long t = Math.floorMod(bus.id - bus.index, bus.id);
            long n = bigN / bus.id;
            result += inverse(n, bus.id) * n * t;
        }
        return result % bigN;
    }

    // Uses a modified extended Euclidean algorithm
    public static long inverse(long a, long mod) {
        long t = 0;
        long newT = 1;
        long r = mod;
        long newR = a;

        while (newR != 0) {
            long quotient = r / newR;
            long temp = t;
            t = newT;
            newT = temp - (quotient * newT);
            temp = r;
            r = newR;
            newR = temp - (quotient * newR);
        }
        return t < 0 ? t + mod : t;
    }

    @Override
    protected void parse() {
        buses = new ArrayList<>();

        String busInput = lines.get(1);
        String[] split = busInput.split(",");
        // Map<Long, Long> closest = new HashMap<>();
        for (int i = 0; i < split.length; i++) {
            String bus = split[i];
            if (bus.equals("x")) {
                continue;
            }
            int id = Integer.parseInt(bus);
            // long available = (earliest / id);
            // if (earliest % id != 0)
            //     available++;
            // long a = id * available;
            buses.add(new Bus(i, id));
            // closest.put(id, a);
        }
    }

    @Value
    private static class Bus {
        int index;
        int id;
    }
}
