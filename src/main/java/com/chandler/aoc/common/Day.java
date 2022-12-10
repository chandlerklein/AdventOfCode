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
    private boolean isExample = false;

    public void printParts() {
        if (part1() != null) {
            System.out.printf("Part 1: %n%s%n", part1());
        }
        if (part2() != null) {
            System.out.printf("Part 2: %n%s%n", part2());
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

    protected String dayString() {
        String className = this.getClass().toString();
        String year = "20%s".formatted(className.substring(27, 29));
        String day = "%s%s".formatted(className.substring(30).toLowerCase(), isExample ? "-example" : "");
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

    protected Day isExample() {
        this.isExample = true;
        return this;
    }
}
