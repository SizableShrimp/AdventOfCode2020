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

import lombok.AllArgsConstructor;
import me.sizableshrimp.adventofcode2020.helper.Processor;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04 extends Day {
    private static final Pattern hex = Pattern.compile("#[0-9a-f]{6}");
    private static final Pattern eyeColors = Pattern.compile("(amb|blu|brn|gry|grn|hzl|oth)");
    private String[] passports;

    @Override
    protected Result evaluate() {
        int part1 = 0;
        int part2 = 0;
        Map<PassportEntry, String> current = new EnumMap<>(PassportEntry.class);

        for (String passport : passports) {
            for (String part : passport.split(" ")) {
                String[] entry = part.split(":");
                String key = entry[0];
                // Skip cid
                if (!key.equals("cid"))
                    current.put(PassportEntry.valueOf(key), entry[1]);
            }

            boolean hasAll = current.size() == 7; // Keys are always valid so we can just check the amount
            if (hasAll) {
                part1++;
                boolean valid = current.entrySet().stream().map(e -> e.getKey().isValid(e.getValue())).reduce(true, (a, b) -> a && b);
                if (valid)
                    part2++;
            }

            current.clear();
        }

        return new Result(part1, part2);
    }

    @Override
    protected void parse() {
        passports = Processor.splitStream(lines, String::isBlank)
                .map(s -> s.collect(Collectors.joining(" ")))
                .toArray(String[]::new);
    }

    @AllArgsConstructor
    private enum PassportEntry {
        byr((e, supp) -> validateInt(e, supp, 1920, 2002)),
        iyr((e, supp) -> validateInt(e, supp, 2010, 2020)),
        eyr((e, supp) -> validateInt(e, supp, 2020, 2030)),
        hgt((e, supp) -> switch (e.substring(e.length() - 2)) {
            case "cm" -> betweenInclusive(Integer.parseInt(e.substring(0, e.length() - 2)), 150, 193);
            case "in" -> betweenInclusive(Integer.parseInt(e.substring(0, e.length() - 2)), 59, 76);
            default -> false;
        }),
        hcl((e, supp) -> hex.matcher(e).matches()),
        ecl((e, supp) -> eyeColors.matcher(e).matches()),
        pid((e, supp) -> {
            // Will throw NFE if invalid
            supp.getAsInt();
            return e.length() == 9;
        });

        private final BiFunction<String, IntSupplier, Boolean> validate;

        private static boolean validateInt(String entry, IntSupplier supplier, int lower, int higher) {
            return entry.length() == 4 && betweenInclusive(supplier.getAsInt(), lower, higher);
        }

        private static boolean betweenInclusive(int in, int lower, int higher) {
            return in >= lower && in <= higher;
        }

        private boolean isValid(String entry) {
            try {
                return validate.apply(entry, () -> Integer.parseInt(entry));
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
