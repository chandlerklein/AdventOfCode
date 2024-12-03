package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

public class Day03 extends Day {

    void main() {
        new Day03().run();
    }

    @Override protected Object part1() {
        return Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(string())
                      .results()
                      .map(result -> parseLong(result.group(1)) * parseLong(result.group(2)))
                      .mapToLong(Long::longValue)
                      .sum();
    }

    @Override protected Object part2() {
        var enabled = new AtomicBoolean(true);
        return Pattern.compile("mul\\((\\d+),(\\d+)\\)|do(n't)?\\(\\)").matcher(string())
                      .results()
                      .<Long>mapMulti((result, mapper) -> {
                          switch (result.group(0)) {
                              case "do()" -> enabled.set(true);
                              case "don't()" -> enabled.set(false);
                              default -> {
                                  if (enabled.get()) {
                                      mapper.accept(parseLong(result.group(1)) * parseLong(result.group(2)));
                                  }
                              }
                          }
                      })
                      .mapToLong(Long::longValue)
                      .sum();
    }

}
