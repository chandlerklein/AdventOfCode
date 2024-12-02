package com.chandler.aoc.year2020;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class Day01 extends Day {

    public static void main(String[] args) {
        new Day01().run();
    }

    @Override
    protected Object part1() {
        long[] nums = stream().mapToLong(Long::parseLong).toArray();
        return Arrays.stream(nums)
                     .flatMap(a -> Arrays.stream(nums)
                                         .filter(b -> a + b == 2020L)
                                         .map(b -> b * a))
                     .findFirst()
                     .orElseThrow();
    }

    @Override
    protected Object part2() {
        long[] nums = stream().mapToLong(Long::parseLong).toArray();
        int target = 2020;

        for (int i = 0; i < nums.length; i++) {
            long currentTarget = target - nums[i];
            Set<Long> existingNums = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                if (existingNums.contains(currentTarget - nums[j])) {
                    return nums[i] * nums[j] * (currentTarget - nums[j]);
                } else {
                    existingNums.add(nums[j]);
                }
            }
        }
        throw new NoSuchElementException("No triad found that adds to 2020");
    }
}
