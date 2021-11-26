package com.chandler.aoc.common;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.apache.commons.io.FileUtils.readFileToString;

public abstract class Day {
    private static final String DEFAULT_DELIMITER = System.lineSeparator();
    private final String year;
    private final String day;

    public Day(String year, String day) {
        this.year = year;
        this.day = day;
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

    private LongStream dayNumberStream() {
        return dayStream().mapToLong(Long::parseLong);
    }

    protected Stream<String> dayStream() {
        return Arrays.stream(day().split(DEFAULT_DELIMITER));
    }

    private String day() {
        return getResourceAsString(year + "/day" + day + ".txt");
    }

    private String getResourceAsString(String fileName) {
        try {
            return readFileToString(new File(Objects.requireNonNull(Day.class.getClassLoader().getResource(fileName)).getFile()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
