package com.chandler.aoc.year23;

import com.chandler.aoc.common.Day;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

public class Day01 extends Day {

    public static void main(String[] args) {
        new Day01().run();
    }

    @Override
    protected Object part1() {
        return stream().map(it -> it.replaceAll("\\D", ""))
                       .map(it -> it.charAt(0) + valueOf(it.charAt(it.length() - 1)))
                       .mapToInt(Integer::parseInt)
                       .sum();
    }

    @Override
    protected Object part2() {
        return stream().map(this::getFirstAndLastDigit)
                       .mapToInt(Integer::parseInt)
                       .sum();
    }

    private final Map<String, String> map = Map.of(
        "one", "1",
        "two", "2",
        "three", "3",
        "four", "4",
        "five", "5",
        "six", "6",
        "seven", "7",
        "eight", "8",
        "nine", "9"
    );

    private String getFirstAndLastDigit(String line) {
        Pattern pattern = Pattern.compile("one|two|three|four|five|six|seven|eight|nine|\\d");
        Matcher matcher = pattern.matcher(line);

        String first = null;
        String last = null;

        while (matcher.find()) {
            if (first == null) {
                first = matcher.group();
            }
            last = matcher.group();
            matcher.region(matcher.start() + 1, line.length());
        }
        return map.getOrDefault(first, first) + map.getOrDefault(last, last);
    }

}
