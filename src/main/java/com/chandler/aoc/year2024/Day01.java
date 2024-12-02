package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;

import java.util.Map;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Day01 extends Day {

    public static void main(String[] args) {
        new Day01().run();
    }

    @Override protected Object part1() {
        var map = stream().map(str -> str.split("\\s{3}"))
                          .map(arr -> Map.entry(parseInt(arr[0]), parseInt(arr[1])))
                          .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        var list1 = map.keySet().stream().sorted().toList();
        var list2 = map.values().stream().sorted().toList();
        return IntStream.range(0, list1.size()).map(i -> Math.abs(list1.get(i) - list2.get(i))).sum();
    }

    @Override protected Object part2() {
        var map = stream().map(str -> str.split("\\s{3}"))
                          .map(arr -> Map.entry(parseInt(arr[0]), parseInt(arr[1])))
                          .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        var freq = map.values().stream().collect(groupingBy(identity(), counting()));
        return map.keySet().stream().map(it -> it * freq.getOrDefault(it, 0L)).mapToLong(Long::valueOf).sum();
    }

}
