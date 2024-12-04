package com.chandler.aoc.year2023;

import com.chandler.aoc.common.Day;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Day09 extends Day {

    public static void main(String[] args) {
        new Day09().run();
    }

    private final List<List<Integer>> nums = stream()
        .map(line -> Arrays.stream(line.split("\\s"))
                           .map(Integer::parseInt)
                           .collect(toList())).toList();

    @Override
    public Object part1() {
        return nums.stream().mapToInt(this::getResult).sum();
    }

    @Override
    public Object part2() {
        return nums.stream().map(Lists::reverse).mapToInt(this::getResult).sum();
    }

    private int getResult(List<Integer> nums) {
        int sum = 0;
        while (true) {
            var diffs = new ArrayList<Integer>();
            boolean allZero = true;
            sum += nums.get(nums.size() - 1);
            for (int i = 0; i < nums.size() - 1; i++) {
                int diff = nums.get(i + 1) - nums.get(i);
                if (diff != 0) allZero = false;
                diffs.add(diff);
            }
            if (allZero) break;
            nums = diffs;
        }
        return sum;
    }

}

