package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.templates.HyperCoordinate;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.HashSet;
import java.util.Set;

public class Day17 extends SeparatedDay {
    private Set<HyperCoordinate> initialState;

    @Override
    protected Object part1() {
        return runSixCycles(false);
    }

    @Override
    protected Object part2() {
        return runSixCycles(true);
    }

    private int runSixCycles(boolean useFourthDim) {
        Set<HyperCoordinate> set = new HashSet<>(initialState);

        for (int cycle = 0; cycle < 6; cycle++) {
            set = runCycle(set, useFourthDim);
        }

        return set.size();
    }

    private Set<HyperCoordinate> runCycle(Set<HyperCoordinate> set, boolean useFourthDim) {
        Set<HyperCoordinate> next = new HashSet<>();
        Set<HyperCoordinate> leftToCheck = new HashSet<>();

        for (HyperCoordinate active : set) {
            int neighbors = getActiveNeighbors(set, active, leftToCheck, useFourthDim);
            if (neighbors == 2 || neighbors == 3)
                next.add(active);
        }
        for (HyperCoordinate inactive : leftToCheck) {
            // If the inactive coord is in the set of active,
            // it isn't inactive and we already checked it.
            if (set.contains(inactive))
                continue;
            int neighbors = getActiveNeighbors(set, inactive, null, useFourthDim);
            if (neighbors == 3)
                next.add(inactive);
        }

        return next;
    }

    private int getActiveNeighbors(Set<HyperCoordinate> set, HyperCoordinate coord, Set<HyperCoordinate> leftToCheck, boolean useFourthDim) {
        int active = 0;
        int startW = useFourthDim ? -1 : 0;
        for (int z = -1; z <= 1; z++) {
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    // If no fourth dimension, just use 0 for w
                    for (int w = startW; useFourthDim ? w <= 1 : w == startW; w++) {
                        if (x == 0 && y == 0 && z == 0 && w == 0)
                            continue;
                        HyperCoordinate neighbor = coord.resolve(x, y, z, w);
                        if (set.contains(neighbor))
                            active++;
                        if (leftToCheck != null)
                            leftToCheck.add(neighbor);
                    }
                }
            }
        }
        return active;
    }

    @Override
    protected void parse() {
        initialState = new HashSet<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#')
                    initialState.add(HyperCoordinate.of(x, y, 0, 0));
            }
        }
    }
}
