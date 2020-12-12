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
import lombok.Value;
import me.sizableshrimp.adventofcode2020.helper.GridHelper;

import java.util.Arrays;

/**
 * A 2-dimensional coordinate object that holds an x and a y value.
 */
@Value
@AllArgsConstructor(staticName = "of")
public class Coordinate {
    public static final Coordinate ORIGIN = new Coordinate(0, 0);
    public int x, y;

    public static Coordinate of(double x, double y) {
        return Coordinate.of((int) x, (int) y);
    }

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

    public Coordinate multiply(Coordinate mut) {
        return multiply(mut.x, mut.y);
    }

    public Coordinate multiply(int factor) {
        return multiply(factor, factor);
    }

    public Coordinate multiply(int xFactor, int yFactor) {
        return new Coordinate(this.x * xFactor, this.y * yFactor);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Coordinate swapXY() {
        return new Coordinate(y, x);
    }

    /**
     * Rotates an (x,y) {@link Coordinate} around the {@link Coordinate#ORIGIN} by {@code degrees} which is a multiple of 90.
     *
     * @param degrees A degree value that is a multiple of 90.
     * @return A rotated {@link Coordinate}.
     */
    public Coordinate rotate90(int degrees) {
        if (degrees < 0)
            degrees = 360 + degrees;
        degrees %= 360;

        return switch (degrees) {
            case 90 -> this.swapXY().multiply(-1, 1);
            case 180 -> this.multiply(-1, -1);
            case 270 -> this.swapXY().multiply(1, -1);
            case 0 -> this;
            default -> throw new IllegalStateException("Degrees is not a multiple of 90: " + degrees);
        };
    }

    public <T> boolean isValid(T[][] grid) {
        return GridHelper.isValid(grid, this);
    }

    /**
     * Finds the Manhattan distance from this coordinate to the origin at (0, 0).
     *
     * @return The Manhattan distance from this coordinate to the origin at (0, 0).
     */
    public int distanceToOrigin() {
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
     * Returns null if the two coordinates are not within one tile in cardinal or ordinal directions away.
     *
     * @param other Another coordinate used to find the relative direction towards.
     * @return The {@link Direction Direction} needed to move from this coordinate to the other coordinate specified,
     * or null if this is not possible in one move in cardinal or ordinal directions.
     */
    public Direction relative(Coordinate other) {
        return Arrays.stream(Direction.cardinalOrdinalDirections()).filter(d -> resolve(d).equals(other)).findAny().orElse(null);
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
