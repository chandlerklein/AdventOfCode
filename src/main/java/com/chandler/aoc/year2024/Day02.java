package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;
import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Gatherers.windowSliding;

public class Day02 extends Day {

    public static void main(String[] args) {
        new Day02().run();
    }

    @Override public Object part1() {
        return stream().map(line -> Arrays.stream(line.split("\\s")).mapToInt(Integer::parseInt).boxed().toList())
                       .filter(this::isSafePart1)
                       .count();
    }

    @Override public Object part2() {
        return stream().map(line -> Arrays.stream(line.split("\\s")).mapToInt(Integer::parseInt).boxed().toList())
                       .filter(this::isSafePart2)
                       .count();
    }

    private boolean isSafePart1(List<Integer> line) {
        var diffs = line.stream().gather(windowSliding(2))
                        .map(window -> window.getLast() - window.getFirst())
                        .collect(toSet());
        return Range.closed(1, 3).containsAll(diffs) || Range.closed(-3, -1).containsAll(diffs);
    }

    // I give up :^)
    private boolean isSafePart2(List<Integer> line) {
        if (isSafePart1(line)) return true;
        return IntStream.range(0, line.size()).anyMatch(i -> {
            List<Integer> sublist = new ArrayList<>(line);
            sublist.remove(i);
            return isSafePart1(sublist);
        });
    }

}
