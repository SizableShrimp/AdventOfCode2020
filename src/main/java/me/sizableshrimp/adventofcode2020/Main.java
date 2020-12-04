/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020;

import me.sizableshrimp.adventofcode2020.helper.DataManager;
import me.sizableshrimp.adventofcode2020.templates.Day;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.lang.reflect.Constructor;
import java.time.Duration;
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
        run(args);
    }

    /**
     * Runs the AOC challenges.
     * <br />
     * If the first argument supplied is "wait", then this code will go into wait mode. This includes waiting until
     * the upcoming challenge opens and downloading the input file automatically between 2 and 30 seconds, inclusive.
     * After downloading the input file, the process ends.
     * <br />
     * If it is the month of December and {@link Main#YEAR} is equal to the current year, then the current day's code is run.
     * Otherwise, runs ALL days by default.
     *
     * @param args
     */
    public static void run(String[] args) {
        if (args.length >= 1 && "wait".equalsIgnoreCase(args[0])) {
            waitForDay();
            return;
        }
        LocalDateTime time = LocalDateTime.now(ZoneId.of("America/New_York"));
        int dayOfMonth = time.getDayOfMonth();

        if (time.getMonth() == Month.DECEMBER && dayOfMonth <= 25) {
            run(dayOfMonth);
        } else {
            runAll();
        }
    }

    //TODO wip
    private static void waitForDay() {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("America/New_York"));
        LocalDateTime release = time.plus(Duration.ofDays(1)).withSecond(0).withMinute(0).withHour(0);
        int dayOfMonth = release.getDayOfMonth();
        if (release.getMonth() != Month.DECEMBER || dayOfMonth > 25 || YEAR != release.getYear())
            throw new IllegalArgumentException("Cannot use \"wait\" if it is not during AOC!");
        System.out.println(release);
        // while (true) {
        //     System.out.println(LocalDateTime.now(ZoneId.of("America/New_York")));
        //     try {
        //         Thread.currentThread().sleep(1000);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }
    }

    /**
     * Runs a specific {@link Day} and prints out the results.
     *
     * @param dayOfMonth The Advent day of the month between 1 and 25, inclusive.
     */
    public static void run(int dayOfMonth) {
        if (dayOfMonth < 1 || dayOfMonth > 25)
            throw new IllegalArgumentException("dayOfMonth cannot be less than 1 or greater than 25!");
        try {
            System.out.println("Day " + dayOfMonth + ":");
            getDayConstructor(dayOfMonth).newInstance().run();
        } catch (ClassNotFoundException ignored) {} catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Constructor<Day> getCurrentDayConstructor() throws ClassNotFoundException, NoSuchMethodException {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("America/New_York"));
        int dayOfMonth = time.getDayOfMonth();

        if (time.getMonth() == Month.DECEMBER && dayOfMonth <= 25) {
            return getDayConstructor(dayOfMonth);

        }

        throw new IllegalStateException("Cannot get current day if it is not during AOC!");
    }

    @SuppressWarnings("unchecked")
    public static Constructor<Day> getDayConstructor(int dayOfMonth) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clazz = Class.forName(BASE_PACKAGE + "Day" + pad(dayOfMonth));
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz.equals(SeparatedDay.class) || superClazz.equals(Day.class)) {
            Class<Day> dayClazz = (Class<Day>) clazz;
            return dayClazz.getDeclaredConstructor();
        } else {
            throw new ClassNotFoundException("Detected day class does not extend SeparatedDay or Day");
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
