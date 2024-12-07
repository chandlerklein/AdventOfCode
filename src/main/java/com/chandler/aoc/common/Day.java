package com.chandler.aoc.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class Day {
    private final Logger log = Logger.getLogger(getClass().getName());

    public void run() {
        Object part1Result = part1();
        Object part2Result = part2();
        log.info(part1Result == null ? "PART 1 - UNCOMPLETED" : "PART 1: %n%s%n".formatted(part1Result));
        log.info(part2Result == null ? "PART 2 - UNCOMPLETED" : "PART 2: %n%s%n".formatted(part2Result));
    }

    public abstract Object part1();

    public abstract Object part2();

    public Stream<String> stream() {
        try {
            return Files.lines(getFilePath());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to get file lines stream");
        }
    }

    public Stream<String> stream(String delimiter) {
        return Pattern.compile(delimiter).splitAsStream(string());
    }

    public String string() {
        Path filePath = getFilePath();
        try {
            return Files.readString(filePath, UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read input file");
        }
    }

    private Path getFilePath() {
        String className = this.getClass().toString();
        String yearDay = className.replaceAll("[a-zA-Z.\\s]", "");
        int year = parseInt(yearDay.substring(0, 4));
        int day = parseInt(yearDay.substring(4));

        Path filePath = Paths.get("src", "main", "resources", "%d-%02d.txt".formatted(year, day));

        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("Input file doesn't exist");
        }
        return filePath;
    }

}
