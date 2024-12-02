package com.chandler.aoc.year2020;

import com.chandler.aoc.common.Day;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Range;

import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Day02 extends Day {

    public static void main(String[] args) {
        new Day02().run();
    }

    @Override
    protected Object part1() {
        return getPolicies().filter(Policy::isValidPart1).count();
    }

    @Override
    protected Object part2() {
        return getPolicies().filter(Policy::isValidPart2).count();
    }

    private Stream<Policy> getPolicies() {
        return stream().map(str -> str.split("[- ]")).map(Policy::new);
    }

    private static class Policy {
        private final int min;
        private final int max;
        private final char letter;
        private final String password;

        public Policy(String[] arr) {
            this.min = parseInt(arr[0]);
            this.max = parseInt(arr[1]);
            this.letter = arr[2].charAt(0);
            this.password = arr[3];
        }

        public boolean isValidPart1() {
            int numMatches = Iterables.size(Splitter.on(letter).split(password));
            return Range.closed(min, max).contains(numMatches);
        }

        public boolean isValidPart2() {
            return password.charAt(min - 1) == letter ^ password.charAt(max - 1) == letter;
        }
    }
}
