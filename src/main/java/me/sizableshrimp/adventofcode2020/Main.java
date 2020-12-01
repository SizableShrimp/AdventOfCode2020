/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020;

import me.sizableshrimp.adventofcode2020.helper.DataManager;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

public class Main {
    /**
     * The hardcoded Advent Of Code year.
     * This year integer is only used for retrieving data from the AOC servers with {@link DataManager#read}.
     * Otherwise, it is useless.
     */
    public static final int YEAR = 2020;
    private static final String BASE_PACKAGE = Main.class.getPackageName() + ".days.";

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("America/New_York"));
        int dayOfMonth = time.getDayOfMonth();

        if (time.getMonth() == Month.DECEMBER && dayOfMonth <= 25) {
            run(dayOfMonth);
        } else {
            runAll();
        }
    }

    private static void run(int dayOfMonth) {
        try {
            Class<?> clazz = Class.forName(BASE_PACKAGE + "Day" + pad(dayOfMonth));
            System.out.println("Day " + dayOfMonth + ":");
            ((Day) clazz.getDeclaredConstructor().newInstance()).run();
        } catch (ClassNotFoundException ignored) {} catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runAll() {
        for (int i = 1; i <= 25; i++) {
            run(i);
        }
    }

    public static String pad(int i) {
        return String.format("%02d", i);
    }
}
