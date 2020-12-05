/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import lombok.AllArgsConstructor;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04 extends Day {
    private final Map<String, PassportEntry> conversion = Arrays.stream(PassportEntry.values())
            .collect(Collectors.toMap(Enum::toString, Function.identity()));

    @Override
    protected Result evaluate() {
        int part1 = 0;
        int part2 = 0;
        Map<String, String> current = new HashMap<>();

        for (String line : lines) {
            if (line.isBlank()) {
                if (validate(current, true)) {
                    part1++;
                }
                if (validate(current, false))
                    part2++;
                current.clear();
                continue;
            }
            for (String part : line.split(" ")) {
                String[] entry = part.split(":");
                String key = entry[0];
                // Skip cid
                if (!key.equals("cid"))
                    current.put(key, entry[1]);
            }
        }
        if (validate(current, true))
            part1++;
        if (validate(current, false))
            part2++;

        return new Result(part1, part2);
    }

    private boolean validate(Map<String, String> current, boolean partOne) {
        boolean hasAll = current.size() == conversion.size(); // Keys are always valid so we can just check the amount
        if (partOne)
            return hasAll;
        if (!hasAll)
            return false;

        return current.entrySet().stream().map(e -> conversion.get(e.getKey()).isValid(e.getValue())).reduce(true, (a, b) -> a && b);
    }

    private static final Pattern hex = Pattern.compile("#[0-9a-f]{6}");
    private static final Pattern eyeColors = Pattern.compile("(amb|blu|brn|gry|grn|hzl|oth)");

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
