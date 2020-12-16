package me.sizableshrimp.adventofcode2020.helper;

import lombok.SneakyThrows;
import me.sizableshrimp.adventofcode2020.Main;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataManager {
    private static String sessionCookie;

    /**
     * The <code>read</code> method is used to locate input data for a specified {@link Day}.
     * <br />
     * This function checks for the existence of a {@link Day} input file first in the <code>days</code> directory included in the jar.
     * If a file is not found, it then checks if the file exists in the <code>aoc_input</code> directory inside of the working directory.
     * For example, class <code>Day01</code> would have a corresponding input text file in "days/day01.txt". If an input text file is found,
     * the data from that file is returned in an unmodifiable list.
     * <br />
     * If no input text file is found, this method then <u>tries to connect to the Advent Of Code servers for input
     * data</u>. If <code>session.txt</code> does not exist, this method will throw an {@link IllegalArgumentException}.
     * This session file should hold your session cookie for the <a href="http://adventofcode.com">Advent Of Code Website</a>.
     * This cookie can be found using browser inspection.
     * <br />
     * If a successful connection is made to the AOC servers, the input data is stored in a file that is located in the
     * working directory in the <code>aoc_input</code> directory in case of later usage. The data fetched from the server
     * originally is then returned in an unmodifiable list.
     *
     * @param day The integer day of which to read input data.
     * @return An unmodifiable {@link List} of strings representing each line of input data.
     * @throws IllegalArgumentException If an existing {@link Day} input file cannot be found and <code>sessions.txt</code> does not exist.
     */
    public static List<String> read(int day) {
        Path path = getPath(day);
        if (path == null)
            return List.of();
        List<String> lines = getDataFromFile(path);

        if (!lines.isEmpty())
            return lines;

        load();

        return getDataFromServer(day, Main.YEAR, path);
    }

    @SneakyThrows
    private static void load() {
        if (sessionCookie != null)
            return;

        Path path = Path.of("session.txt");

        if (!Files.exists(path))
            throw new IllegalArgumentException("No AOC session cookie found! Please create session.txt");

        sessionCookie = Files.readString(path).trim();
    }

    // public static Result send(int day, Part part, int year, String result) {
    //
    // }

    private static List<String> getDataFromServer(int day, int year, Path path) {
        List<String> lines = new ArrayList<>();

        try {
            URI uri = new URI("https://adventofcode.com/" + year + "/day/" + day + "/input");

            HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("User-Agent",
                            "SizableShrimp-AOC-Data-Bot/2.0.2.0 (+http://github.com/SizableShrimp/AdventOfCode2020)")
                    .header("Cookie", "session=" + sessionCookie)
                    .build();
            HttpResponse<Stream<String>> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofLines());

            lines = response.body().collect(Collectors.toList());
            if (path != null)
                writeFile(path, lines);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.copyOf(lines);
    }

    public static void writeFile(Path path, List<String> lines) {
        Path parent = path.getParent();
        try {
            if (!Files.exists(parent))
                Files.createDirectory(parent);
            //remove empty last line of input files although this doesn't really matter? hmm
            Files.writeString(path, String.join(System.lineSeparator(), lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getDataFromFile(Path path) {
        try {
            if (path != null && Files.exists(path)) {
                return List.copyOf(Files.readAllLines(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of();
    }

    private static Path getPath(int day) {
        String filename = "day" + Main.pad(day) + ".txt";

        URL url = Main.class.getResource("/days/" + filename);
        if (url == null) {
            return getBasePath(filename);
        }

        try {
            return Path.of(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Path getBasePath(String filename) {
        return Path.of("aoc_input", filename);
    }

    /**
     * Reads all input data for a given year from the server using the provided AOC session cookie
     * and saves it to running directory subfolder "aoc_input". See {@link #read} for more detail.
     *
     * @param year The Advent Of Code year to read input data for each day.
     */
    public static void writeAllDaysToFile(int year) {
        for (int i = 1; i <= 25; i++) {
            String filename = "day" + Main.pad(i) + ".txt";
            Path path = getBasePath(filename);
            getDataFromServer(i, year, path);
        }
    }

    // @Value
    // private static class Result {
    //     String message;
    //     boolean correct;
    // }
    //
    // private enum Part {
    //     FIRST,
    //     SECOND,
    //     /**
    //      * Sends the second part if both parts are non-null, otherwise the first part
    //      */
    //     AUTO
    // }
}
