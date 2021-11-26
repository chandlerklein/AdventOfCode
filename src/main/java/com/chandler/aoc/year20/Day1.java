package com.chandler.aoc.year20;

import com.chandler.aoc.common.Day;

import java.util.Arrays;

public class Day1 extends Day {

    public Day1() {
        super("2020", "1");
    }

    public static void main(String[] args) {
        new Day1().printParts();
    }

    @Override
    protected Object part1() {
        long[] nums = dayNumbers();
        return Arrays.stream(nums)
                     .flatMap(a -> Arrays.stream(nums)
                                         .filter(b -> a + b == 2020L)
                                         .map(b -> b * a))
                     .findFirst()
                     .orElseThrow();
    }

    @Override
    protected Object part2() {
        return null;
    }
}
