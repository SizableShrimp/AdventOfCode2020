/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.helper;

import me.sizableshrimp.adventofcode2020.templates.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern pattern = Pattern.compile("(\\d+,\\d+)");

    /**
     * Attempts to find every coordinate in the provided line and returns them in a list, in order.
     *
     * @param line The provided line to search for coordinates.
     * @return A list of {@link Coordinate}s found in the provided line, in order.
     */
    public static List<Coordinate> parseCoordinates(String line) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            coordinates.add(Coordinate.parse(matcher.group(1)));
        }

        return coordinates;
    }

    /**
     * Parses each line from the input list using the provided pattern and {@link Matcher#matches()}.
     *
     * @param pattern The pattern to use for matching to each line.
     * @param lines The list of lines to parse.
     * @param consumer The consumer to use the matcher and line data returned for each line.
     */
    public static void parseLines(Pattern pattern, List<String> lines, BiConsumer<Matcher, String> consumer) {
        lines.forEach(line -> {
            Matcher m = pattern.matcher(line);
            m.matches();
            consumer.accept(m, line);
        });
    }

    /**
     * Parses each line from the input list using the provided pattern and {@link Matcher#find}.
     *
     * @param pattern The pattern to use for matching to each line.
     * @param lines The list of lines to parse.
     * @param consumer The consumer to use the matcher and line data returned for each line.
     */
    public static void parseLinesFind(Pattern pattern, List<String> lines, BiConsumer<Matcher, String> consumer) {
        lines.forEach(line -> {
            Matcher m = pattern.matcher(line);
            m.find();
            consumer.accept(m, line);
        });
    }
}
