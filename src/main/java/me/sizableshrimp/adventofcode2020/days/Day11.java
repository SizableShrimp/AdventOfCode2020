/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.sizableshrimp.adventofcode2020.helper.GridHelper;
import me.sizableshrimp.adventofcode2020.templates.Coordinate;
import me.sizableshrimp.adventofcode2020.templates.Direction;
import me.sizableshrimp.adventofcode2020.templates.EnumState;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

public class Day11 extends SeparatedDay {
    State[][] initial;

    @Override
    protected Object part1() {
        return runUntilSame(4, false);
    }

    @Override
    protected Object part2() {
        return runUntilSame(5, true);
    }

    private Object runUntilSame(int overcrowded, boolean raytrace) {
        State[][] grid = GridHelper.copy(initial);

        while (true) {
            State[][] next = nextIteration(grid, overcrowded, raytrace);
            // GridHelper.print(next);
            // System.out.println("\n\n\n");

            if (GridHelper.equals(grid, next)) {
                return GridHelper.countOccurrences(grid, State.FILLED);
            }
            grid = next;
        }
    }

    private State[][] nextIteration(State[][] grid, int overcrowded, boolean raytrace) {
        int yLength = grid.length;
        int xLength = grid[0].length;
        State[][] next = new State[yLength][xLength];

        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                State state = grid[y][x];
                if (state == State.FLOOR) {
                    next[y][x] = State.FLOOR;
                    continue;
                }
                int count = countAdjacent(grid, raytrace, x, y);

                next[y][x] = switch (state) {
                    case EMPTY -> count == 0 ? State.FILLED : State.EMPTY;
                    case FILLED -> count >= overcrowded ? State.EMPTY : State.FILLED;
                    default -> throw new IllegalStateException();
                };
            }
        }

        return next;
    }

    private int countAdjacent(State[][] grid, boolean raytrace, int x, int y) {
        int count = 0;

        for (Direction dir : Direction.cardinalOrdinalDirections()) {
            Coordinate temp = new Coordinate(x, y).resolve(dir);
            while (temp.isValid(grid)) {
                State s = grid[temp.y][temp.x];
                // If it is a seat OR we aren't raytracing, always break
                if (s != State.FLOOR || !raytrace) {
                    if (s == State.FILLED)
                        count++;
                    break;
                }
                temp = temp.resolve(dir);
            }
        }

        return count;
    }

    @Override
    protected void parse() {
        initial = GridHelper.convert((y, x) -> new State[y][x], lines);
    }

    @Getter
    @AllArgsConstructor
    private enum State implements EnumState<State> {
        FLOOR('.'),
        EMPTY('L'),
        FILLED('#');

        char mappedChar;
    }
}
