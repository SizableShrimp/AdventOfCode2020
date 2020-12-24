package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.templates.Coordinate;
import me.sizableshrimp.adventofcode2020.templates.Direction;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day24 extends SeparatedDay {
    // Not the same as normal directions
    private static final Map<String, Direction> HEX_DIRECTIONS = Map.of(
            "e", Direction.EAST,
            "se", Direction.SOUTH,
            "sw", Direction.SOUTHWEST,
            "w", Direction.WEST,
            "nw", Direction.NORTH,
            "ne", Direction.NORTHEAST
    );
    private Set<Coordinate> tiles;

    @Override
    protected Object part1() {
        tiles = new HashSet<>();

        for (String line : lines) {
            Coordinate current = Coordinate.ORIGIN;

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                String s = String.valueOf(c);

                if (c == 'n' || c == 's') {
                    s += line.charAt(i + 1);
                    // Skip the next char
                    i++;
                }

                current = current.resolve(HEX_DIRECTIONS.get(s));
            }

            boolean isBlack = tiles.contains(current);
            if (isBlack) {
                tiles.remove(current);
            } else {
                tiles.add(current);
            }
        }

        return tiles.size();
    }

    @Override
    protected Object part2() {
        // Part 2 starts based off of Part 1's hex grid

        for (int day = 1; day <= 100; day++) {
            Set<Coordinate> nextTiles = new HashSet<>();
            Set<Coordinate> leftToCheck = new HashSet<>();

            for (Coordinate current : tiles) {
                countNeighborsAndUpdate(current, true, nextTiles, leftToCheck);
            }
            for (Coordinate current : leftToCheck) {
                countNeighborsAndUpdate(current, false, nextTiles, null);
            }

            tiles = nextTiles;
        }

        return tiles.size();
    }

    private void countNeighborsAndUpdate(Coordinate current, boolean isBlack, Set<Coordinate> nextTiles, Set<Coordinate> leftToCheck) {
        int blackNeighbors = 0;

        for (Direction dir : HEX_DIRECTIONS.values()) {
            Coordinate neighbor = current.resolve(dir);
            boolean isNeighborBlack = tiles.contains(neighbor);
            if (isNeighborBlack) {
                blackNeighbors++;
            } else if (leftToCheck != null) {
                // We still have to check the neighbor if it is a white tile
                leftToCheck.add(neighbor);
            }
        }

        if (isBlack) {
            if (blackNeighbors <= 2 && blackNeighbors != 0) {
                nextTiles.add(current);
            }
        } else {
            if (blackNeighbors == 2) {
                nextTiles.add(current);
            }
        }
    }
}
