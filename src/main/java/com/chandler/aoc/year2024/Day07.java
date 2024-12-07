package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.Map;

import static java.lang.Long.parseLong;
import static java.util.Map.entry;

public class Day07 extends Day {

    void main() {
        new Day07().run();
    }

    @Override public Object part1() {
        return stream().map(str -> str.split(": "))
                       .map(i -> entry(parseLong(i[0]), Arrays.stream(i[1].split(" ")).mapToLong(Long::parseLong).toArray()))
                       .filter(entry -> isValid(entry.getKey(), entry.getValue(), 1, entry.getValue()[0]))
                       .mapToLong(Map.Entry::getKey)
                       .sum();
    }

    @Override public Object part2() {
        return stream().map(str -> str.split(": "))
                       .map(i -> entry(parseLong(i[0]), Arrays.stream(i[1].split(" ")).mapToLong(Long::parseLong).toArray()))
                       .filter(entry -> isValidPartTwo(entry.getKey(), entry.getValue(), 1, entry.getValue()[0]))
                       .mapToLong(Map.Entry::getKey)
                       .sum();
    }

    private boolean isValid(long target, long[] nums, int i, long currentVal) {
        if (i >= nums.length) return currentVal == target;
        if (isValid(target, nums, i + 1, currentVal + nums[i])) return true;
        return isValid(target, nums, i + 1, currentVal * nums[i]);
    }

    private boolean isValidPartTwo(long target, long[] nums, int i, long currentVal) {
        if (i >= nums.length) return currentVal == target;
        if (isValidPartTwo(target, nums, i + 1, parseLong("%d%d".formatted(currentVal, nums[i])))) return true;
        if (isValidPartTwo(target, nums, i + 1, currentVal * nums[i])) return true;
        return isValidPartTwo(target, nums, i + 1, currentVal + nums[i]);
    }

}
