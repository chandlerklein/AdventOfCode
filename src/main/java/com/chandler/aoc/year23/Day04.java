package com.chandler.aoc.year23;

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
    protected Object part1() {
        return stream().mapToInt(this::getPoints).sum();
    }

    @Override
    protected Object part2() {
        int[] cards = new int[221];

        String[] lines = stream().toArray(String[]::new);

        int i = 1;
        for (String line : lines) {
            int matches = (int) getMatches(line);
            int numCards = ++cards[i];
            IntStream.rangeClosed(i + 1, i + matches)
                     .forEach(j -> cards[j] += numCards);
            i++;
        }
        return Arrays.stream(cards).sum();
    }

    private int getPoints(String line) {
        double matches = getMatches(line);
        return (int) Math.pow(2, matches - 1);
    }

    private double getMatches(String line) {
        String[] allNums = line.substring(10).trim().split(" \\| ");
        var winningNums = Arrays.stream(allNums[0].split("\\s{1,2}")).collect(toSet());
        var nums = Arrays.stream(allNums[1].split("\\s{1,2}")).collect(toSet());
        return Sets.intersection(winningNums, nums).size();
    }

}
