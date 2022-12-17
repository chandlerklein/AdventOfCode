package com.chandler.aoc.year21;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.Set;

public class Day08 extends Day {

    private static final Set<Integer> uniqueSegmentCounts = Set.of(2, 3, 4, 7);

    public static void main(String[] args) {
        new Day08().printParts();
    }

    @Override
    protected Object part1() {
        return dayStream().map(line -> line.substring(61))
                          .map(outputValues -> outputValues.split(" "))
                          .flatMap(Arrays::stream)
                          .map(String::length)
                          .filter(uniqueSegmentCounts::contains)
                          .count();
    }

    @Override
    protected Object part2() {
        // nope 🤠
        return null;
    }
}
