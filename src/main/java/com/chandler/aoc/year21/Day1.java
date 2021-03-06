package com.chandler.aoc.year21;

import com.chandler.aoc.common.Day;

public class Day1 extends Day {

    public Day1() {
        super("2021", "1");
    }

    public static void main(String[] args) {
        new Day1().printParts();
    }

    @Override
    protected Object part1() {
        long[] nums = dayNumbers();
        int result = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                result++;
            }
        }
        return result;
    }

    @Override
    protected Object part2() {
        long[] nums = dayNumbers();
        int result = 0;

        for (int i = 0; i < nums.length - 3; i++) {
            if (nums[i] < nums[i + 3]) {
                result++;
            }
        }
        return result;
    }
}
