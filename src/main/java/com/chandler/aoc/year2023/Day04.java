package com.chandler.aoc.year2023;

import com.chandler.aoc.common.Day;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class Day04 extends Day {

    public static void main(String[] args) {
        new Day04().run();
    }

    @Override
    public Object part1() {
        return stream().mapToInt(line -> (int) Math.pow(2, getMatches(line) - 1)).sum();
    }

    @Override
    public Object part2() {
        int[] cards = new int[221];
        int i = 1;
        int total = 0;

        for (String line : stream().toArray(String[]::new)) {
            int matches = (int) getMatches(line);
            int numCards = ++cards[i];
            total += numCards;
            IntStream.rangeClosed(i + 1, i + matches).forEach(j -> cards[j] += numCards);
            i++;
        }
        return total;
    }

    private double getMatches(String line) {
        String[] allNums = line.substring(10).trim().split(" \\| ");
        var winningNums = Arrays.stream(allNums[0].split("\\s+")).collect(toSet());
        var nums = Arrays.stream(allNums[1].split("\\s+")).collect(toSet());
        return Sets.intersection(winningNums, nums).size();
    }

}
