package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;

import java.util.Arrays;

import static java.lang.Math.abs;

public class Day02 extends Day {

    public static void main(String[] args) {
        new Day02().run();
    }

    @Override protected Object part1() {
        return stream().map(line -> Arrays.stream(line.split("\\s")).mapToInt(Integer::parseInt).toArray())
                       .filter(this::isSafePart1)
                       .count();
    }

    @Override protected Object part2() {
        return stream().map(line -> Arrays.stream(line.split("\\s")).mapToInt(Integer::parseInt).toArray())
                       .count();
    }

    private boolean isSafePart1(int[] line) {
        int firstDiff = line[1] - line[0];
        if (firstDiff == 0 || abs(firstDiff) > 3) return false;
        boolean isIncreasing = firstDiff > 0;
        for (int i = 2; i < line.length; i++) {
            int diff = line[i] - line[i - 1];
            if (isIncreasing && (diff > 3 || diff < 1)) return false;
            if (!isIncreasing && (diff < -3 || diff > -1)) return false;
        }
        return true;
    }

}
