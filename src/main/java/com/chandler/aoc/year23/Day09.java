package com.chandler.aoc.year23;

import com.chandler.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09 extends Day {

    public static void main(String[] args) {
        new Day09().run();
    }

    private final List<List<Integer>> nums = stream()
        .map(line -> Arrays.stream(line.split("\\s"))
                           .map(Integer::parseInt)
                           .toList()).toList();

    @Override
    protected Object part1() {
        return nums.stream().mapToInt(it -> getResult(it, false)).sum();
    }

    @Override
    protected Object part2() {
        return nums.stream().mapToInt(it -> getResult(it, true)).sum();
    }

    private int getResult(List<Integer> nums, boolean isPartTwo) {
        int sum = 0;
        var firstNums = new ArrayList<Integer>();
        while (true) {
            var diffs = new ArrayList<Integer>();
            boolean allZero = true;
            firstNums.add(nums.get(0));
            sum += nums.get(nums.size() - 1);
            for (int i = 0; i < nums.size() - 1; i++) {
                int diff = nums.get(i + 1) - nums.get(i);
                if (diff != 0) allZero = false;
                diffs.add(diff);
            }
            if (allZero) break;
            nums = diffs;
        }
        if (!isPartTwo) return sum;

        int first = 0;
        for (int i = firstNums.size() - 1; i >= 0; i--) {
            first = firstNums.get(i) - first;
        }
        return first;
    }

}

