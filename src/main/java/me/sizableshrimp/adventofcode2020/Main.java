package me.sizableshrimp.adventofcode2020;

import me.sizableshrimp.adventofcode2020.helper.DataManager;
import me.sizableshrimp.adventofcode2020.templates.Day;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    /**
     * The hardcoded Advent Of Code year.
     * This year integer is only used for retrieving data from the AOC servers with {@link DataManager#read}.
     * Otherwise, it is useless.
     */
    public static final int YEAR = 2020;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
    private static final String BASE_PACKAGE = Main.class.getPackageName() + ".days.";

    public static void main(String[] args) {
        run(args);
    }

    /**
     * Runs the AOC challenges.
     * <br />
     * If "wait" is supplied as an argument, then the main thread will wait until the next day unlocks. This includes waiting until
     * the upcoming challenge opens and downloading the input file automatically between 2 and 30 seconds, inclusive.
     * After downloading the input file, the process ends.
     * <br />
     * If the first argument supplied is "all", then this code will run ALL existing days and return.
     * <br />
     * If it is the month of December and {@link Main#YEAR} is equal to the current year, then the current day's code is run.
     * If the first argument supplied is "upload", then this will upload the results of the current day and
     * output whether it was correct. <b>NOTE: Uploading currently does not work.</b>
     * Otherwise, runs ALL days by default.
     *
     * @param args Used to modify what is executed.
     */
    public static void run(String[] args) {
        List<String> list = Arrays.asList(args);
        if (list.contains("wait")) {
            waitForDay();
            return;
        }
        if (list.contains("all")) {
            runAll();
            return;
        }
        // TODO fix
        boolean upload = false; // list.contains("upload");
        LocalDateTime time = LocalDateTime.now(ZoneId.of("America/New_York"));
        int dayOfMonth = time.getDayOfMonth();

        if (time.getMonth() == Month.DECEMBER && dayOfMonth <= 25) {
            run(dayOfMonth, upload);
        } else {
            runAll();
        }
    }

    private static void waitForDay() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/New_York"));
        LocalDateTime release = now.plus(Duration.ofDays(1)).withNano(0).withSecond(0).withMinute(0).withHour(0);
        int dayOfMonth = release.getDayOfMonth();
        if (release.getMonth() != Month.DECEMBER || dayOfMonth > 25 || YEAR != release.getYear())
            throw new IllegalArgumentException("Cannot use \"wait\" if it is not during AOC!");
        System.out.println("Releases at " + release.format(FORMATTER));
        try {
            boolean doneThisMinute = false;
            while (true) {
                now = LocalDateTime.now(ZoneId.of("America/New_York"));
                if (now.getMinute() % 5 == 0) {
                    if (!doneThisMinute) {
                        System.out.println(now.format(FORMATTER));
                        doneThisMinute = true;
                    }
                } else {
                    doneThisMinute = false;
                }
                if (now.isAfter(release)) {
                    int rand = ThreadLocalRandom.current().nextInt(10, 31);
                    Thread.sleep(rand * 1000L);
                    DataManager.read(dayOfMonth);
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void run(int dayOfMonth) {
        run(dayOfMonth, false);
    }

    /**
     * Runs a specific {@link Day} and prints out the results.
     *
     * @param dayOfMonth The Advent day of the month between 1 and 25, inclusive.
     * @param upload Whether to attempt to upload the result to the server.
     */
    public static void run(int dayOfMonth, boolean upload) {
        if (dayOfMonth < 1 || dayOfMonth > 25)
            throw new IllegalArgumentException("dayOfMonth cannot be less than 1 or greater than 25!");
        try {
            System.out.println("Day " + dayOfMonth + ":");
            Day.Result result = getDayConstructor(dayOfMonth).newInstance().run();
            if (upload) {
                // TODO fix
                // DataManager.upload(result, dayOfMonth);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("The selected class, Day" + pad(dayOfMonth) + ", does not exist.", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static String pad(int i) {
        return String.format("%02d", i);
    }

    public static Constructor<Day> getCurrentDayConstructor() throws ClassNotFoundException, NoSuchMethodException {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("America/New_York"));
        int dayOfMonth = time.getDayOfMonth();

        if (time.getMonth() == Month.DECEMBER && dayOfMonth <= 25) {
            return getDayConstructor(dayOfMonth);

        }

        throw new IllegalStateException("Cannot get current day if it is not during AOC!");
    }

    private static void runAll() {
        Map<Integer, Day> days = new HashMap<>();
        for (int i = 1; i <= 25; i++) {
            try {
                days.put(i, getDayConstructor(i).newInstance());
            } catch (ClassNotFoundException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("All Days\n\n");
        long before = System.nanoTime();
        for (Map.Entry<Integer, Day> entry : days.entrySet()) {
            System.out.println("Day " + entry.getKey() + ":");
            entry.getValue().run();
        }
        long after = System.nanoTime();
        System.out.printf("Completed all days in %.3fms%n%n", (after - before) / 1_000_000f);
    }
}
