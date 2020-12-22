package me.sizableshrimp.adventofcode2020.days;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import me.sizableshrimp.adventofcode2020.helper.GridHelper;
import me.sizableshrimp.adventofcode2020.helper.MultiMap;
import me.sizableshrimp.adventofcode2020.helper.Parser;
import me.sizableshrimp.adventofcode2020.helper.Processor;
import me.sizableshrimp.adventofcode2020.templates.Coordinate;
import me.sizableshrimp.adventofcode2020.templates.Direction;
import me.sizableshrimp.adventofcode2020.templates.EnumState;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Day20 extends SeparatedDay {
    private Map<Integer, Tile[][]> tiles;
    private MultiMap<List<Tile>, Integer> collisions;
    private List<Integer> corners;
    private int tileSize;
    private static final Tile[][] SEA_MONSTER = GridHelper.convert((y, x) -> new Tile[y][x],
            List.of("..................#.",
                    "#....##....##....###",
                    ".#..#..#..#..#..#..."));
    private static final Tile[][] SEA_MONSTER_REVERSED_X = GridHelper.reflectX((y, x) -> new Tile[y][x], SEA_MONSTER);
    private static final Tile[][] SEA_MONSTER_REVERSED_Y = GridHelper.reflectY((y, x) -> new Tile[y][x], SEA_MONSTER);

    @Override
    protected Object part1() {
        collisions = new MultiMap<>();

        for (Map.Entry<Integer, Tile[][]> entry : tiles.entrySet()) {
            Map<Direction, List<Tile>> borders = extractBorders(entry.getValue());
            int id = entry.getKey();
            for (List<Tile> edge : borders.values()) {
                collisions.putValue(edge, id);
                collisions.putValue(reverse(edge), id);
            }
        }

        corners = new ArrayList<>();
        long value = 1;
        for (Map.Entry<Integer, Tile[][]> entry : tiles.entrySet()) {
            Map<Direction, List<Tile>> borders = extractBorders(entry.getValue());
            int count = 0;
            for (List<Tile> edge : borders.values()) {
                if (collisions.get(edge).size() == 2) {
                    count++;
                }
            }
            // It is a corner if exactly 2 edges match
            if (count == 2) {
                value *= entry.getKey();
                corners.add(entry.getKey());
            }
        }

        return value;
    }

    @Override
    protected Object part2() {
        int size = (int) Math.sqrt(tiles.size());
        Tile[][][][] bigGrid = new Tile[size][size][][];
        Deque<Node> bfs = new ArrayDeque<>();

        int cornerId = corners.get(0);
        Tile[][] cornerGrid = placeFirstCorner(cornerId, bfs);
        // int[][] ids = new int[size][size];
        // ids[0][0] = cornerId;
        bigGrid[0][0] = cornerGrid;
        Set<Integer> seen = new HashSet<>();
        seen.add(cornerId);

        while (!bfs.isEmpty()) {
            Node node = bfs.pop();
            if (!seen.add(node.nextId))
                continue;
            // System.out.println(node);
            Coordinate coord = node.prev.resolve(node.dir);
            if (coord.y < 0 || coord.y >= size || coord.x < 0 || coord.x >= size || bigGrid[coord.y][coord.x] != null) {
                throw new IllegalStateException();
            }
            Tile[][] grid = tiles.get(node.nextId);
            // GridHelper.print(grid);
            Map<Direction, List<Tile>> borders = extractBorders(grid);
            Direction target = node.dir.opposite();
            // Find the current direction of the edge
            Direction current = null;
            for (Map.Entry<Direction, List<Tile>> entry : borders.entrySet()) {
                List<Tile> edge = entry.getValue();
                List<Tile> reverse = reverse(edge);
                if (edge.equals(node.edge) || reverse.equals(node.edge)) {
                    current = entry.getKey();
                    break;
                }
            }
            if (current == null)
                throw new IllegalStateException();
            // Rotate to get the edges to match up
            if (current != target) {
                int degrees = target.degrees - current.degrees;
                current = current.relativeDegrees(degrees);
                grid = GridHelper.rotate((y, x) -> new Tile[y][x], grid, degrees);
                borders = extractBorders(grid);
            }
            // If it's not the same, we must have a reversed edge so we have to reflect (flip) accordingly
            if (!borders.get(current).equals(node.edge)) {
                if (current == Direction.NORTH || current == Direction.SOUTH) {
                    grid = GridHelper.reflectX((y, x) -> new Tile[y][x], grid);
                } else {
                    grid = GridHelper.reflectY((y, x) -> new Tile[y][x], grid);
                }
                borders = extractBorders(grid);
            }

            bigGrid[coord.y][coord.x] = grid;
            // ids[coord.y][coord.x] = node.nextId;
            // printBig(size, bigGrid);

            // Add other tiles to check
            for (Map.Entry<Direction, List<Tile>> entry : borders.entrySet()) {
                Direction dir = entry.getKey();
                if (dir == current)
                    continue;
                List<Tile> nextEdge = entry.getValue();
                Set<Integer> values = collisions.get(nextEdge);
                if (values.size() == 2) {
                    int other = getOtherId(node.nextId, values);
                    bfs.add(new Node(coord, nextEdge, dir, other));
                }
            }
        }

        int monsterSize = size * (tileSize - 2);
        Tile[][] findMonsters = new Tile[monsterSize][monsterSize];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Tile[][] grid = bigGrid[y][x];
                placeRelative(findMonsters, x, y, grid);
            }
        }
        // GridHelper.print(findMonsters);
        // printBig(size, cornerGrid, bigGrid);
        return getRoughness(findMonsters);
    }

    private void placeRelative(Tile[][] findMonsters, int x, int y, Tile[][] grid) {
        int offsetY = y * (grid.length - 2);
        int offsetX = x * (grid[0].length - 2);
        for (int innerY = 1; innerY < grid.length - 1; innerY++) {
            for (int innerX = 1; innerX < grid[0].length - 1; innerX++) {
                findMonsters[offsetY + innerY - 1][offsetX + innerX - 1] = grid[innerY][innerX];
            }
        }
    }

    private Tile[][] placeFirstCorner(int cornerId, Deque<Node> bfs) {
        Set<Direction> targetRotation = EnumSet.of(Direction.EAST, Direction.SOUTH);
        Tile[][] cornerGrid = tiles.get(cornerId);

        while (true) {
            cornerGrid = GridHelper.rotate((y, x) -> new Tile[y][x], cornerGrid, 90);
            // GridHelper.print(cornerGrid);
            Map<Direction, List<Tile>> borders = extractBorders(cornerGrid);
            Set<Direction> dirs = new HashSet<>();
            Set<Node> next = new HashSet<>();

            for (Map.Entry<Direction, List<Tile>> entry : borders.entrySet()) {
                Direction dir = entry.getKey();
                List<Tile> edge = entry.getValue();
                Set<Integer> edgeOverlap = collisions.get(edge);

                if (edgeOverlap.size() == 2) {
                    dirs.add(dir);
                    next.add(new Node(Coordinate.ORIGIN, edge, dir, getOtherId(cornerId, edgeOverlap)));
                }
            }
            if (dirs.equals(targetRotation)) {
                bfs.addAll(next);
                break;
            }
        }

        return cornerGrid;
    }

    private int getRoughness(Tile[][] findMonsters) {
        int roughness = GridHelper.countOccurrences(findMonsters, Tile.WALL);
        int monsterCount = GridHelper.countOccurrences(SEA_MONSTER, Tile.WALL);
        for (int degrees = 0; degrees <= 270; degrees += 90) {
            Tile[][] find = GridHelper.rotate((y, x) -> new Tile[y][x], findMonsters, degrees);
            long before = roughness;
            roughness -= findSeaMonsters(monsterCount, find, SEA_MONSTER);
            roughness -= findSeaMonsters(monsterCount, find, SEA_MONSTER_REVERSED_X);
            roughness -= findSeaMonsters(monsterCount, find, SEA_MONSTER_REVERSED_Y);
            if (before != roughness)
                break;
        }
        return roughness;
    }

    private int getOtherId(int currentId, Set<Integer> values) {
        int other = -1;
        for (int id : values) {
            if (currentId != id) {
                other = id;
                break;
            }
        }
        return other;
    }

    private List<Tile> reverse(List<Tile> list) {
        List<Tile> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }

    private int findSeaMonsters(long monsterCount, Tile[][] find, Tile[][] seaMonster) {
        int result = 0;
        for (int y = 0; y < find.length - seaMonster.length; y++) {
            for (int x = 0; x < find[0].length - seaMonster[0].length; x++) {
                boolean valid = true;
                for (int innerY = 0; innerY < seaMonster.length; innerY++) {
                    for (int innerX = 0; innerX < seaMonster[0].length; innerX++) {
                        Tile t = seaMonster[innerY][innerX];
                        if (t == Tile.WALL && find[y + innerY][x + innerX] != Tile.WALL) {
                            valid = false;
                            break;
                        }
                    }
                    if (!valid)
                        break;
                }
                if (valid)
                    result += monsterCount;
            }
        }
        return result;
    }

    private Map<Direction, List<Tile>> extractBorders(Tile[][] grid) {
        Map<Direction, List<Tile>> borders = new EnumMap<>(Direction.class);
        Function<Direction, List<Tile>> newList = ignored -> new ArrayList<>();
        for (int y = 0; y < grid.length; y++) {
            borders.computeIfAbsent(Direction.WEST, newList).add(grid[y][0]);
            borders.computeIfAbsent(Direction.EAST, newList).add(grid[y][grid[0].length - 1]);
        }
        for (int x = 0; x < grid[0].length; x++) {
            borders.computeIfAbsent(Direction.NORTH, newList).add(grid[0][x]);
            borders.computeIfAbsent(Direction.SOUTH, newList).add(grid[grid.length - 1][x]);
        }
        return borders;
    }

    @Override
    protected void parse() {
        tiles = new HashMap<>();
        List<List<String>> input = Processor.split(lines, String::isBlank);

        for (List<String> tile : input) {
            int id = Parser.parseMatch("Tile (\\d+):", tile.get(0)).groupInt(1);
            Tile[][] grid = GridHelper.convert((y, x) -> new Tile[y][x], tile.subList(1, tile.size()));
            tileSize = grid.length;
            tiles.put(id, grid);
        }
    }

    // Helpful for debugging the test input
    private void printBig(int size, Tile[][][][] bigGrid) {
        int bigSize = size * tileSize;
        for (int y = 0; y < bigSize; y++) {
            if (y % tileSize == 0)
                System.out.println();
            for (int x = 0; x < bigSize; x++) {
                if (x % tileSize == 0)
                    System.out.print(" ");
                Tile[][] grid = bigGrid[y / tileSize][x / tileSize];
                if (grid == null) {
                    System.out.print(" ");
                    continue;
                }
                System.out.print(grid[y % tileSize][x % tileSize].mappedChar);
            }
            System.out.println();
        }
    }

    @Value
    private static class Node {
        Coordinate prev;
        List<Tile> edge;
        Direction dir;
        int nextId;
    }

    @AllArgsConstructor
    private enum Tile implements EnumState<Tile> {
        // Not accurate names but who cares :)
        SPACE('.'),
        WALL('#');

        @Getter
        final char mappedChar;
    }
}
