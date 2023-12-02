package com.chandler.aoc.year20;

import com.chandler.aoc.common.Day;

import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.Range.between;
import static org.apache.commons.lang3.StringUtils.countMatches;

public class Day02 extends Day {

    public static void main(String[] args) {
        new Day02().run();
    }

    @Override
    protected Object part1() {
        return getPolicies().filter(Policy::isValidPart1)
                            .count();
    }

    @Override
    protected Object part2() {
        return getPolicies().filter(Policy::isValidPart2)
                            .count();
    }

    private Stream<Policy> getPolicies() {
        return stream().map(str -> str.split("[- ]"))
                          .map(Policy::new);
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
            return between(min, max)
                .contains(countMatches(password, letter));
        }

        public boolean isValidPart2() {
            return password.charAt(min - 1) == letter
                ^ password.charAt(max - 1) == letter;
        }
    }
}
