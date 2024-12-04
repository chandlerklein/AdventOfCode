package com.chandler.aoc.year2022;

import com.chandler.aoc.common.Day;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

public class Day10 extends Day {

    public static void main(String[] args) {
        new Day10().run();
    }

    private final String[] lines = string().split("\r\n");

    @Override
    public Object part2() {
        int x = 1;
        int crtPosition = 0;
        List<Character> characters = new ArrayList<>();

        for (String line : lines) {
            String[] row = line.split(" ");
            characters.add(Set.of(x - 1, x, x + 1).contains(crtPosition++ % 40) ? '#' : '.');
            if (row[0].equals("addx")) {
                characters.add(Set.of(x - 1, x, x + 1).contains(crtPosition++ % 40) ? '#' : '.');
                x += parseInt(row[1]);
            }
        }
        return Lists.partition(characters, 40)
                    .stream()
                    .map(chars -> chars.stream().map(String::valueOf).collect(joining()))
                    .collect(joining(lineSeparator()));
    }

    @Override
    public Object part1() {
        return null;
    }
}
