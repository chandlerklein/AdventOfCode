package com.chandler.aoc.year23;

import com.chandler.aoc.common.Day;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;

public class Day06 extends Day {

    public static void main(String[] args) {
        new Day06().run();
    }

    private final Pattern digits = Pattern.compile("\\d+");
    private final List<String> lines = stream().toList();

    @Override
    protected Object part1() {
        return getAllWaysToWin(digits.matcher(lines.get(0)).results().map(MatchResult::group).map(Long::parseLong).toList(),
                               digits.matcher(lines.get(1)).results().map(MatchResult::group).map(Long::parseLong).toList())
            .reduce(Math::multiplyExact)
            .orElseThrow();
    }

    @Override
    protected Object part2() {
        return getAllWaysToWin(List.of(parseLong(digits.matcher(lines.get(0)).results().map(MatchResult::group).collect(joining()))),
                               List.of(parseLong(digits.matcher(lines.get(1)).results().map(MatchResult::group).collect(joining()))))
            .findFirst().orElseThrow();
    }

    private LongStream getAllWaysToWin(List<Long> durations, List<Long> records) {
        return IntStream.range(0, durations.size())
                        .mapToLong(i -> IntStream.iterate(1, j -> j < durations.get(i), j -> j + 1)
                                                 .mapToLong(j -> j * (durations.get(i) - j))
                                                 .filter(totalDistance -> totalDistance > records.get(i))
                                                 .count());
    }

}
