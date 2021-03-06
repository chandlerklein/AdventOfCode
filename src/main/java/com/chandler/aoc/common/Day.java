package com.chandler.aoc.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.apache.commons.io.FileUtils.readFileToString;

public abstract class Day {
    private static final String DEFAULT_DELIMITER = System.lineSeparator();
    private final String year;
    private final String dayOfMonth;

    protected Day(String year, String dayOfMonth) {
        this.year = year;
        this.dayOfMonth = dayOfMonth;
    }

    protected void printParts() {
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
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
        return getResourceAsString(year + "/day" + dayOfMonth + ".txt");
    }

    private String getResourceAsString(String fileName) {
        try {
            return readFileToString(
                    new File(Objects.requireNonNull(Day.class.getClassLoader().getResource(fileName)).getFile()),
                    Charset.defaultCharset());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
