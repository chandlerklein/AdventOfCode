package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.String.valueOf;
import static java.util.function.Predicate.not;
import static java.util.stream.Gatherers.windowFixed;

public class Day09 extends Day {

    void main() {
        new Day09().run();
    }

    List<Integer> nums =
        Arrays.stream(string().split("")).filter(not(String::isBlank)).map(Integer::parseInt).toList();

    @Override public Object part1() {
        StringBuilder sb = new StringBuilder();
        var k = new AtomicInteger(48);
        nums.stream()
            .gather(windowFixed(2))
            .forEach(window -> {
                if (window.getFirst() != null) sb.append(valueOf((char) k.getAndIncrement()).repeat(window.getFirst()));
                if (window.size() == 2) sb.append(".".repeat(window.getLast()));
            });
        char[] arr = sb.toString().toCharArray();
        int i = 0;
        int j = arr.length - 1;
        while (i < j) {
            while (arr[i] != '.') i++;
            while (arr[j] == '.') j--;
            if (i >= j) break;
            char temp = arr[j];
            arr[i] = temp;
            arr[j] = '.';
        }
        return IntStream.range(0, i).mapToLong(l -> (long) l * (arr[l] - 48)).sum();
    }

    @Override public Object part2() {
        return null;
    }

}
