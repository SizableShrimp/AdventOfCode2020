/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.templates;

import lombok.Value;

/**
 * A 3-dimensional coordinate object that holds an x, a y, and a z value.
 */
@Value
public class ZCoordinate {
    public static final ZCoordinate ZERO = new ZCoordinate(0, 0, 0);
    public int x, y, z;

    /**
     * Creates a new {@link ZCoordinate} based on the offset of x, y, and z given in parameters added to the current
     * coordinate.
     *
     * @param x The offset-x to add to the coordinate x.
     * @param y The offset-y to add to the coordinate y.
     * @param z The offset-z to add to the coordinate z.
     * @return A new {@link ZCoordinate} created from the sum of the current coordinates and offset values provided.
     */
    public ZCoordinate resolve(int x, int y, int z) {
        return new ZCoordinate(this.x + x, this.y + y, this.z + z);
    }

    public ZCoordinate resolve(ZCoordinate other) {
        return resolve(other.x, other.y, other.z);
    }

    /**
     * Finds the Manhattan distance from this coordinate to (0, 0, 0).
     *
     * @return The Manhattan distance from this coordinate to (0, 0, 0).
     */
    public int distanceZero() {
        return distance(0, 0, 0);
    }

    /**
     * Finds the Manhattan distance from this coordinate to (x2, y2, z2).
     *
     * @param x2 The x of the other coordinate.
     * @param y2 The y of the other coordinate.
     * @param z2 The z of the other coordinate.
     * @return The Manhattan distance from this coordinate to (x2, y2, z2).
     */
    public int distance(int x2, int y2, int z2) {
        return Math.abs(x - x2) + Math.abs(y - y2) + Math.abs(z - z2);
    }

    /**
     * Finds the Manhattan distance from this coordinate to the other coordinate specified.
     *
     * @param other The coordinate object to compare with.
     * @return The Manhattan distance from this coordinate to the other coordinate specified.
     */
    public int distance(ZCoordinate other) {
        return distance(other.x, other.y, other.z);
    }

    /**
     * Parses a coordinate in the format "x,y,z".
     *
     * @param coord The input string of which to parse a coordinate.
     * @return A new {@link Coordinate} object.
     */
    public static ZCoordinate parse(String coord) {
        String[] arr = coord.split(",");
        int x = Integer.parseInt(arr[0]);
        int y = Integer.parseInt(arr[1]);
        int z = Integer.parseInt(arr[2]);
        return new ZCoordinate(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d,%d)", x, y, z);
    }
}
