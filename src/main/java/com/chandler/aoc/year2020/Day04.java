package com.chandler.aoc.year2020;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.Set;

public class Day04 extends Day {

    public static void main(String[] args) {
        new Day04().run();
    }

    private static final Set<String> requiredFields = Set.of("byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:");

    @Override
    protected Object part1() {
        return getNumInvalidPassports();
    }

    private long getNumInvalidPassports() {
        String[] passports = string().split("\r\n\r\n");

        return passports.length - Arrays.stream(passports)
                                        .filter(passport -> requiredFields.stream().anyMatch(field -> !passport.contains(field)))
                                        .count();
    }

    @Override
    protected Object part2() {
        return null;
    }
}
