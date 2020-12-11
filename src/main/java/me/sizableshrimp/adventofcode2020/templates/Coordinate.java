/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.templates;

import lombok.Value;
import me.sizableshrimp.adventofcode2020.helper.GridHelper;

import java.util.Arrays;

/**
 * A 2-dimensional coordinate object that holds an x and a y value.
 */
@Value
public class Coordinate {
    public static final Coordinate ZERO = new Coordinate(0, 0);
    public int x, y;

    /**
     * Creates a new {@link Coordinate} based on the offset of x and y given in parameters added to the current
     * coordinate.
     *
     * @param x The offset-x to add to the coordinate x.
     * @param y The offset-y to add to the coordinate y.
     * @return A new {@link Coordinate} made from adding the offset-x to the base x and offset-y to the base y.
     */
    public Coordinate resolve(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    public Coordinate resolve(Coordinate other) {
        return resolve(other.x, other.y);
    }

    public Coordinate resolve(Direction direction) {
        return resolve(direction.x, direction.y);
    }

    public <T> boolean isValid(T[][] grid) {
        return GridHelper.isValid(grid, this);
    }

    /**
     * Finds the Manhattan distance from this coordinate to (0, 0).
     *
     * @return The Manhattan distance from this coordinate to (0, 0).
     */
    public int distanceZero() {
        return distance(0, 0);
    }

    /**
     * Finds the Manhattan distance from this coordinate to (x2, y2).
     *
     * @param x2 The x of the other coordinate.
     * @param y2 The y of the other coordinate.
     * @return The Manhattan distance from this coordinate to (x2, y2).
     */
    public int distance(int x2, int y2) {
        return Math.abs(x - x2) + Math.abs(y - y2);
    }

    /**
     * Finds the Manhattan distance from this coordinate to the other coordinate specified.
     *
     * @param other The coordinate object to compare with.
     * @return The Manhattan distance from this coordinate to the other coordinate specified.
     */
    public int distance(Coordinate other) {
        return distance(other.x, other.y);
    }

    /**
     * Finds the relative {@link Direction Direction} needed to get from this coordinate to the other coordinate specified.
     * Returns null if the two coordinates are not within one tile in cardinal directions away.
     *
     * @param other Another coordinate used to find the relative direction towards.
     * @return The {@link Direction Direction} needed to move from this coordinate to the other coordinate specified,
     * or null if this is not possible in one move in cardinal directions.
     */
    public Direction relative(Coordinate other) {
        return Arrays.stream(Direction.values()).filter(d -> resolve(d).equals(other)).findAny().orElse(null);
    }

    /**
     * Parses a coordinate in the format "x,y".
     *
     * @param coord The input string of which to parse a coordinate.
     * @return A new {@link Coordinate} object.
     */
    public static Coordinate parse(String coord) {
        String[] arr = coord.split(",");
        int x = Integer.parseInt(arr[0]);
        int y = Integer.parseInt(arr[1]);
        return new Coordinate(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
}
