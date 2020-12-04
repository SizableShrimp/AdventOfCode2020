/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.days;

import com.svetylkovo.rojo.Rojo;
import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Mapper;
import com.svetylkovo.rojo.annotations.Regex;
import com.svetylkovo.rojo.matcher.RojoBeanMatcher;
import lombok.Data;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.function.Function;

public class Day02 extends Day {
    @Override
    protected Result evaluate() {
        int validCount = 0;
        int positionCount = 0;
        RojoBeanMatcher<Input> matcher = Rojo.of(Input.class);
        for (String line : lines) {
            Input input = matcher.match(line).get();
            long num = input.password.chars()
                    .filter(c -> c == input.policy)
                    .count();
            if (num >= input.least && num <= input.most)
                validCount++;
            // XOR
            if ((input.password.charAt(input.least - 1) == input.policy) != (input.password.charAt(input.most - 1) == input.policy))
                positionCount++;
        }

        return new Result(validCount, positionCount);
    }

    @Override
    protected void parse() {

    }

    @Data
    @Regex("(\\d+)-(\\d+) (\\w): (\\w+)")
    public static class Input {
        @Group(1)
        int least;
        @Group(2)
        int most;
        @Group(3)
        @Mapper(CharMapper.class)
        char policy;
        @Group(4)
        String password;
    }

    public static class CharMapper implements Function<String, Character> {
        @Override
        public Character apply(String s) {
            return s.charAt(0);
        }
    }
}
