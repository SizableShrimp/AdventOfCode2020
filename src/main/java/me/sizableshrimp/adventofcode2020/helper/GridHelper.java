package me.sizableshrimp.adventofcode2020.helper;

import me.sizableshrimp.adventofcode2020.templates.Coordinate;
import me.sizableshrimp.adventofcode2020.templates.EnumState;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.function.Predicate;

public class GridHelper {
    /**
     * @param generator A {@link BiFunction} that provides two numbers in the form (y, x) and should output a 2d array of type {@link T}.
     * @param lines The list of lines to convert.
     * @param func The function to transform a specific character into type {@link T}
     * @param <T> The array type to be converted to from the list of lines.
     * @return A 2d-array holding objects of type {@link T} converted from {@code func}
     * where the outer array holds the y-coordinate and the inner array holds the x-coordinate.
     */
    public static <T> T[][] convert(BiFunction<Integer, Integer, T[][]> generator, List<String> lines, Function<Character, T> func) {
        T[][] grid = generator.apply(lines.size(), lines.get(0).length());
        return convert(grid, lines, func);
    }

    public static <T> T[][] convert(T[][] grid, List<String> lines, Function<Character, T> func) {
        convert(lines, (y, x, c) -> grid[y][x] = func.apply(c));
        return grid;
    }

    private static void convert(List<String> lines, GridConsumer consumer) {
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            char[] chars = line.toCharArray();
            for (int x = 0; x < chars.length; x++) {
                consumer.accept(y, x, chars[x]);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T> & EnumState<T>> T[][] convert(BiFunction<Integer, Integer, T[][]> generator, List<String> lines) {
        T[][] grid = generator.apply(lines.size(), lines.get(0).length());
        T[] enumConstants = ((Class<T>) grid.getClass().getComponentType().getComponentType()).getEnumConstants();
        return convert(grid, lines, c -> Parser.parseEnumState(enumConstants, c));
    }

    public static boolean[][] convertBool(BiFunction<Integer, Integer, boolean[][]> generator, List<String> lines, Predicate<Character> pred) {
        boolean[][] grid = generator.apply(lines.size(), lines.get(0).length());
        return convertBool(grid, lines, pred);
    }

    public static boolean[][] convertBool(boolean[][] grid, List<String> lines, Predicate<Character> pred) {
        convert(lines, (y, x, c) -> grid[y][x] = pred.test(c));
        return grid;
    }

    public static int[][] convertInt(BiFunction<Integer, Integer, int[][]> generator, List<String> lines, IntFunction<Character> func) {
        int[][] grid = generator.apply(lines.size(), lines.get(0).length());
        return convertInt(grid, lines, func);
    }

    public static int[][] convertInt(int[][] grid, List<String> lines, IntFunction<Character> func) {
        convert(lines, (y, x, c) -> grid[y][x] = func.apply(c));
        return grid;
    }

    public static long[][] convertLong(BiFunction<Integer, Integer, long[][]> generator, List<String> lines, LongFunction<Character> func) {
        long[][] grid = generator.apply(lines.size(), lines.get(0).length());
        return convertLong(grid, lines, func);
    }

    public static long[][] convertLong(long[][] grid, List<String> lines, LongFunction<Character> func) {
        convert(lines, (y, x, c) -> grid[y][x] = func.apply(c));
        return grid;
    }

    public static <T> void print(T[][] grid) {
        for (T[] ts : grid) {
            for (T t : ts) {
                System.out.print(t);
            }
            System.out.println();
        }
    }

    public static <T extends Enum<T> & EnumState<T>> void print(T[][] grid) {
        for (T[] ts : grid) {
            for (T t : ts) {
                System.out.print(t.getMappedChar());
            }
            System.out.println();
        }
    }

    public static <T> int countOccurrences(T[][] grid, T target) {
        int result = 0;
        for (T[] row : grid) {
            for (T t : row) {
                if (target.equals(t))
                    result++;
            }
        }
        return result;
    }

    public static int countOccurrences(int[][] grid, int target) {
        int result = 0;
        for (int[] row : grid) {
            for (int i : row) {
                if (i == target)
                    result++;
            }
        }
        return result;
    }

    public static <T> boolean isValid(T[][] grid, Coordinate coord) {
        return isValid(grid, coord.x, coord.y);
    }

    public static <T> boolean isValid(T[][] grid, int x, int y) {
        return y >= 0 && y < grid.length && x >= 0 && x < grid[0].length;
    }

    public static boolean allFalse(boolean[][] grid) {
        return countOccurrences(grid, false) == (grid.length * grid[0].length);
    }

    public static int countOccurrences(boolean[][] grid, boolean target) {
        int result = 0;
        for (boolean[] row : grid) {
            for (boolean b : row) {
                if (b == target)
                    result++;
            }
        }
        return result;
    }

    /**
     * Copy {@code base} into {@code copy}.
     */
    public static <T> T[][] copy(T[][] base, T[][] copy) {
        for (int i = 0; i < base.length; i++) {
            T[] ts = base[i];
            copy[i] = Arrays.copyOf(ts, ts.length);
        }
        return copy;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[][] copy(T[][] base) {
        T[][] copy = (T[][]) Array.newInstance(base.getClass().getComponentType(), base.length);
        for (int i = 0; i < base.length; i++) {
            T[] ts = base[i];
            copy[i] = Arrays.copyOf(ts, ts.length);
        }
        return copy;
    }

    public static <T> boolean equals(T[][] a, T[][] b) {
        return Arrays.deepEquals(a, b);
    }

    public static boolean equals(boolean[][] a, boolean[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j])
                    return false;
            }
        }
        return true;
    }

    @FunctionalInterface
    public interface GridConsumer {
        void accept(int y, int x, char c);
    }
}
