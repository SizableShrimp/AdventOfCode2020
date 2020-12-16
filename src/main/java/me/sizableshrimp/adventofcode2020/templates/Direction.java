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

package me.sizableshrimp.adventofcode2020.templates;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * The eight cardinal and ordinal directions, associated with degrees and relative x, y positions
 * where NORTH is at 0 degrees and a relative x,y of (0,-1).
 */
@AllArgsConstructor
public enum Direction {
    NORTH(0, 0, -1), NORTHEAST(45, 1, -1),
    EAST(90, 1, 0), SOUTHEAST(135, 1, 1),
    SOUTH(180, 0, 1), SOUTHWEST(225, -1, 1),
    WEST(270, -1, 0), NORTHWEST(315, -1, -1);

    private static final Map<Integer, Direction> degreesMap = new HashMap<>();
    private static final Direction[] CARDINAL = {NORTH, EAST, SOUTH, WEST};
    private static final Direction[] ORDINAL = {NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};
    private static final Direction[] CARDINAL_ORDINAL = {NORTH, EAST, SOUTH, WEST, NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};

    static {
        for (Direction dir : values()) {
            degreesMap.put(dir.degrees, dir);
        }
    }

    public final int degrees;
    public final int x;
    public final int y;

    public static Map<Character, Direction> getCardinalDirections(char up, char right, char down, char left) {
        return Map.of(up, NORTH, right, EAST, down, SOUTH, left, WEST);
    }

    public static Direction parseDirection(int xDiff, int yDiff) {
        for (Direction dir : Direction.values()) {
            if (dir.x == xDiff && dir.y == yDiff)
                return dir;
        }
        throw new IllegalArgumentException();
    }

    public static Direction getCardinalDirection(char c) {
        return switch (c) {
            case 'N' -> NORTH;
            case 'E' -> EAST;
            case 'S' -> SOUTH;
            case 'W' -> WEST;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    public static Direction[] cardinalDirections() {
        return CARDINAL;
    }

    /**
     * @return {@link Direction#NORTHEAST}, {@link Direction#SOUTHEAST}, {@link Direction#SOUTHWEST},{@link Direction#NORTHWEST}
     */
    public static Direction[] ordinalDirections() {
        return ORDINAL;
    }

    public static Direction[] cardinalOrdinalDirections() {
        return CARDINAL_ORDINAL;
    }

    public Direction opposite() {
        return fromDegrees(this.degrees + 180);
    }

    public static Direction fromDegrees(int degrees) {
        if (degrees < 0)
            degrees = 360 + degrees;
        degrees %= 360;

        Direction dir = degreesMap.get(degrees);
        if (dir == null)
            throw new IllegalArgumentException("Invalid degrees: " + degrees);
        return dir;
    }

    public Coordinate asCoords() {
        return Coordinate.of(this.x, this.y);
    }

    public Direction relativeDegrees(int degrees) {
        return fromDegrees(this.degrees + degrees);
    }
}
