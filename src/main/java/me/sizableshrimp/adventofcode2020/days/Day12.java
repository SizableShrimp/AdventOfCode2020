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
