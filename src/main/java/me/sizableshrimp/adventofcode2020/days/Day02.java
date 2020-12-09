/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 extends Day {
    @Override
    protected Result evaluate() {
        int validCount = 0;
        int positionCount = 0;
        // RojoBeanMatcher<Input> matcher = Rojo.of(Input.class);
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");

        for (String line : lines) {
            // Input input = matcher.match(line).get();
            Matcher matcher = pattern.matcher(line);
            matcher.matches();

            int least = Integer.parseInt(matcher.group(1));
            int most = Integer.parseInt(matcher.group(2));
            char policy = matcher.group(3).charAt(0);
            String password = matcher.group(4);

            long num = password.chars()
                    .filter(c -> c == policy)
                    .count();

            if (num >= least && num <= most)
                validCount++;
            // XOR
            if ((password.charAt(least - 1) == policy) != (password.charAt(most - 1) == policy))
                positionCount++;
        }

        return new Result(validCount, positionCount);
    }

    // @Data
    // @Regex("(\\d+)-(\\d+) (\\w): (\\w+)")
    // public static class Input {
    //     @Group(1)
    //     int least;
    //     @Group(2)
    //     int most;
    //     @Group(3)
    //     @Mapper(CharMapper.class)
    //     char policy;
    //     @Group(4)
    //     String password;
    // }
    //
    // public static class CharMapper implements Function<String, Character> {
    //     @Override
    //     public Character apply(String s) {
    //         return s.charAt(0);
    //     }
    // }
}
