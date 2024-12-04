package com.chandler.aoc.year2021;

import com.chandler.aoc.common.Day;

public class Day01 extends Day {

    public static void main(String[] args) {
        new Day01().run();
    }

    @Override
    public Object part1() {
        long[] nums = stream().mapToLong(Long::parseLong).toArray();
        int result = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                result++;
            }
        }
        return result;
    }

    @Override
    public Object part2() {
        long[] nums = stream().mapToLong(Long::parseLong).toArray();
        int result = 0;

        for (int i = 0; i < nums.length - 3; i++) {
            if (nums[i] < nums[i + 3]) {
                result++;
            }
        }
        return result;
    }
}
