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
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");
        int validCount = 0;
        int positionCount = 0;
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            m.matches();
            int least = Integer.parseInt(m.group(1));
            int most = Integer.parseInt(m.group(2));
            char ch = m.group(3).charAt(0);
            String password = m.group(4);
            long num = password.chars()
                    .filter(c -> c == ch)
                    .count();
            if (num >= least && num <= most)
                validCount++;
            // XOR
            if ((password.charAt(least-1) == ch) != (password.charAt(most-1) == ch))
                positionCount++;
        }

        return new Result(validCount, positionCount);
    }

    @Override
    protected void parse() {

    }
}
