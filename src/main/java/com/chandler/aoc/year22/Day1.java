package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Day1 extends Day {

    public Day1() {
        super("2022", "1");
    }

    public static void main(String[] args) {
        new Day1().printParts();
    }

    @Override
    protected Object part1() {
        List<Integer> totalItems = new ArrayList<>();
        int currentAmount = 0;
        for (String line : dayString().replace("\r", "").split("\n")) {
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
        for (String line : dayString().replace("\r", "").split("\n")) {
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