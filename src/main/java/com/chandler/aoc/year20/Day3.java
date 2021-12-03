package com.chandler.aoc.year20;

import com.chandler.aoc.common.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Day3 extends Day {

    private final Object[] lines = dayStream().toArray();

    public Day3() {
        super("2020", "3");
    }

    public static void main(String[] args) {
        new Day3().printParts();
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
        var slopes = List.of(
                Pair.of(1, 1),
                Pair.of(3, 1),
                Pair.of(5, 1),
                Pair.of(7, 1),
                Pair.of(1, 2)
        );
        return slopes.stream()
                     .map(this::getHits)
                     .reduce(1L, (a, b) -> a * b);
    }

    private long getHits(Pair<Integer, Integer> slope) {
        final char tree = '#';
        int hits = 0;
        int x = 0;

        for (int i = slope.getRight(); i < lines.length; i += slope.getRight()) {
            x += slope.getLeft();
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
