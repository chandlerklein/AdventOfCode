package com.chandler.aoc.common;

import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.io.FileUtils.readFileToString;

public abstract class Day {
    private static final String DEFAULT_DELIMITER = System.lineSeparator();

    public void printParts() {
        Object part1Result = part1();
        Object part2Result = part2();

        if (part1Result != null) {
            System.out.printf("Part 1: %n%s%n", part1Result);
        }
        if (part2Result != null) {
            System.out.printf("Part 2: %n%s%n", part2Result);
        }
    }

    protected abstract Object part1();

    protected abstract Object part2();

    protected long[] dayNumbers() {
        return dayNumberStream().toArray();
    }

    protected LongStream dayNumberStream() {
        return dayStream().mapToLong(Long::parseLong);
    }

    protected Stream<String> dayStream() {
        return Pattern.compile(DEFAULT_DELIMITER).splitAsStream(dayString());
    }

    protected Stream<String> dayStream(String delimiter) {
        return Pattern.compile(delimiter).splitAsStream(dayString());
    }

    protected Stream<String> exampleDayStream() {
        return Pattern.compile(DEFAULT_DELIMITER).splitAsStream(exampleDayString());
    }

    protected String dayString() {
        String className = this.getClass().toString();
        String year = "20%s".formatted(className.substring(27, 29));
        String day = className.substring(30).toLowerCase();
        String fileName = "%s/%s.txt".formatted(year, day);
        return getResourceAsString(fileName);
    }

    protected String exampleDayString() {
        String className = this.getClass().toString();
        String year = "20%s".formatted(className.substring(27, 29));
        String day = "%s%s".formatted(className.substring(30).toLowerCase(), "-example");
        String fileName = "%s/%s.txt".formatted(year, day);
        return getResourceAsString(fileName);
    }

    private String getResourceAsString(String fileName) {
        try {
            return readFileToString(
                new File(requireNonNull(Day.class.getClassLoader().getResource(fileName)).getFile()),
                defaultCharset()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Unable to retrieve file: %s".formatted(fileName), e);
        }
    }
}
