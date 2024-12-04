package com.chandler.aoc.year2021;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.Set;

public class Day08 extends Day {

    private static final Set<Integer> uniqueSegmentCounts = Set.of(2, 3, 4, 7);

    public static void main(String[] args) {
        new Day08().run();
    }

    @Override
    public Object part1() {
        return stream().map(line -> line.substring(61))
                          .map(outputValues -> outputValues.split(" "))
                          .flatMap(Arrays::stream)
                          .map(String::length)
                          .filter(uniqueSegmentCounts::contains)
                          .count();
    }

    @Override
    public Object part2() {
        // nope 🤠
        return null;
    }
}
