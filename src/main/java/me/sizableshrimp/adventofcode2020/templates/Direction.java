/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.templates;

import lombok.AllArgsConstructor;

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

    private static final Direction[] CARDINAL = {NORTH, EAST, SOUTH, WEST};
    private static final Direction[] ORDINAL = {NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};
    private static final Direction[] CARDINAL_ORDINAL = {NORTH, EAST, SOUTH, WEST, NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};

    public final int degrees;
    public final int x;
    public final int y;

    public static Direction getDirection(int degrees) {
        if (degrees < 0)
            degrees = 360 + degrees;
        degrees %= 360;
        for (Direction direction : values()) {
            if (direction.degrees == degrees)
                return direction;
        }

        return Direction.NORTH;
    }

    public static Map<Character, Direction> getCardinalDirections(char up, char right, char down, char left) {
        return Map.of(up, NORTH, right, EAST, down, SOUTH, left, WEST);
    }

    public Direction opposite() {
        return values()[(ordinal() + 2) % values().length];
    }

    public static Direction parseDirection(int xDiff, int yDiff) {
        for (Direction dir : Direction.values()) {
            if (dir.x == xDiff && dir.y == yDiff)
                return dir;
        }
        throw new IllegalArgumentException();
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
}
