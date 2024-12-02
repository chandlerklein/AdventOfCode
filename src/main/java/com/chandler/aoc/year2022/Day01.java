package com.chandler.aoc.year2022;

import com.chandler.aoc.common.Day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Day01 extends Day {

    public static void main(String[] args) {
        new Day01().run();
    }

    @Override
    protected Object part1() {
        List<Integer> totalItems = new ArrayList<>();
        int currentAmount = 0;
        for (String line : string().replace("\r", "").split("\n")) {
            if (line.isBlank()) {
                totalItems.add(currentAmount);
                currentAmount = 0;
            } else {
                currentAmount += Integer.parseInt(line);
            }
        }
        return totalItems.stream()
                         .mapToInt(Integer::valueOf)
                         .max()
                         .orElseThrow();
    }

    @Override
    protected Object part2() {
        PriorityQueue<Integer> totalItems = new PriorityQueue<>(Collections.reverseOrder());
        int currentAmount = 0;
        for (String line : string().replace("\r", "").split("\n")) {
            if (line.isBlank()) {
                totalItems.add(currentAmount);
                currentAmount = 0;
            } else {
                currentAmount += Integer.parseInt(line);
            }
        }
        return IntStream.range(0, 3)
                        .map(i -> totalItems.poll())
                        .sum();
    }
}
