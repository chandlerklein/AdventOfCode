package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.util.HashSet;
import java.util.Set;

public class Day06 extends Day {

    public static void main(String[] args) {
        new Day06().run();
    }

    private Object getStartOfMessageMarker(int n) {
        String input = string();

        for (int i = 0; i < input.length() - (n - 1); i++) {
            Set<Character> uniquePacketChars = new HashSet<>(
                input.substring(i, i + n).chars().mapToObj(c -> (char) c).toList()
            );
            if (uniquePacketChars.size() == n) {
                return i + n;
            }
        }
        return null;
    }

    @Override
    protected Object part1() {
        return getStartOfMessageMarker(4);
    }

    @Override
    protected Object part2() {
        return getStartOfMessageMarker(14);
    }
}
