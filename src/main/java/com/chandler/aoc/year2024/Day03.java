package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Long.parseLong;
import static java.util.regex.Pattern.compile;

public class Day03 extends Day {

    void main() {
        new Day03().run();
    }

    @Override protected Object part1() {
        return compile("mul\\((\\d+),(\\d+)\\)")
            .matcher(string()).results()
            .map(result -> parseLong(result.group(1)) * parseLong(result.group(2)))
            .mapToLong(Long::longValue)
            .sum();
    }

    @Override protected Object part2() {
        var enabled = new AtomicBoolean(true);
        return compile("mul\\((\\d+),(\\d+)\\)|do(n't)?\\(\\)")
            .matcher(string()).results()
            .<Long>mapMulti((it, mapper) -> {
                switch (it.group(0)) {
                    case "do()" -> enabled.set(true);
                    case "don't()" -> enabled.set(false);
                    case String _ when enabled.get() -> mapper.accept(parseLong(it.group(1)) * parseLong(it.group(2)));
                    default -> { /* ignore */ }
                }
            })
            .mapToLong(Long::longValue)
            .sum();
    }

}
