package com.chandler.aoc.common;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.google.common.io.Files.asCharSource;
import static java.lang.Integer.parseInt;
import static java.nio.charset.Charset.defaultCharset;

public abstract class Day {

    public void run() {
        Object part1Result = part1();
        Object part2Result = part2();

        System.out.println(part1Result == null ? "PART 1 - UNCOMPLETED" : "PART 1: %n%s%n".formatted(part1Result));
        System.out.println(part2Result == null ? "PART 2 - UNCOMPLETED" : "PART 2: %n%s%n".formatted(part2Result));
    }

    protected Object part1() { return null; }

    protected Object part2() { return null; }

    public Stream<String> stream() {
        return Arrays.stream(string().split("\n"));
    }

    public Stream<String> stream(String delimiter) {
        return Pattern.compile(delimiter).splitAsStream(string());
    }

    public String string() {
        int[] yearDay = getYearDay();
        int year = yearDay[0];
        int day = yearDay[1];

        String filename = "%d-%02d.txt".formatted(year, day);

        String cookie = getFileString("cookie.txt");

        Path filePath = Path.of("src/main/resources/%s".formatted(filename));

        if (Files.exists(filePath)) {
            return getFileString(filename);
        }

        try {
            URL url = new URL("https://adventofcode.com/%d/day/%d/input".formatted(year, day));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setRequestProperty("Cookie", "session=%s".formatted(cookie));

            try (InputStream inputStream = connection.getInputStream()) {
                ByteStreams.copy(inputStream, new FileOutputStream(filePath.toFile()));
            }
            return getFileString(filename);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static String getFileString(String filename) {
        try {
            return asCharSource(new File("src/main/resources/%s".formatted(filename)), defaultCharset()).read();
        } catch (IOException e) {
            throw new RuntimeException("Could not read file");
        }
    }

    private int[] getYearDay() {
        String className = this.getClass().toString();

        return new int[]{
            parseInt("20%s".formatted(className.substring(27, 29))),
            parseInt(className.substring(33).toLowerCase()) };
    }

}
