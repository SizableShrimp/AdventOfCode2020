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

package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.templates.Coordinate;
import me.sizableshrimp.adventofcode2020.templates.Direction;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

public class Day12 extends SeparatedDay {
    @Override
    protected Object part1() {
        return runNavigation(Direction.EAST.asCoords(), true);
    }

    @Override
    protected Object part2() {
        return runNavigation(Coordinate.of(10, -1), false);
    }

    private int runNavigation(Coordinate startWaypoint, boolean moveShip) {
        Coordinate ship = Coordinate.ORIGIN;
        Coordinate waypoint = startWaypoint;

        for (String line : lines) {
            char c = line.charAt(0);
            int num = Integer.parseInt(line.substring(1));
            switch (c) {
                case 'N', 'E', 'S', 'W' -> {
                    Direction temp = Direction.getCardinalDirection(c);
                    Coordinate mult = Coordinate.of(temp.x * num, temp.y * num);
                    if (moveShip)
                        ship = ship.resolve(mult);
                    else
                        waypoint = waypoint.resolve(mult);
                }
                case 'L' -> waypoint = waypoint.rotate90(-num);
                case 'R' -> waypoint = waypoint.rotate90(num);
                case 'F' -> ship = ship.resolve(waypoint.x * num, waypoint.y * num); // Multiplies waypoint x,y by num and adds it to the ship
            }
        }

        return ship.distanceToOrigin();
    }
}
