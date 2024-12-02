package com.chandler.aoc.year2020;

import com.chandler.aoc.common.Day;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class Day03 extends Day {

    private final Object[] lines = stream().toArray();

    public static void main(String[] args) {
        new Day03().run();
    }

    @Override
    protected Object part1() {
        final char tree = '#';
        int hits = 0;
        int x = 0;

        for (int i = 1; i < lines.length; i++) {
            x += 3;
            int strLen = lines[i].toString().length();
            if (x >= strLen) {
                x -= strLen;
            }
            if (lines[i].toString().charAt(x) == tree) {
                hits++;
            }
        }
        return hits;
    }

    @Override
    protected Object part2() {
        return Stream
            .of(entry(1, 1),
                entry(3, 1),
                entry(5, 1),
                entry(7, 1),
                entry(1, 2))
            .map(this::getHits)
            .reduce(1L, (a, b) -> a * b);
    }

    private long getHits(Map.Entry<Integer, Integer> slope) {
        final char tree = '#';
        int hits = 0;
        int x = 0;

        for (int i = slope.getKey(); i < lines.length; i += slope.getValue()) {
            x += slope.getKey();
            int strLen = lines[i].toString().length();
            if (x >= strLen) {
                x -= strLen;
            }
            if (lines[i].toString().charAt(x) == tree) {
                hits++;
            }
        }
        return hits;
    }
}
